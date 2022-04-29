package com.sspu_consultor.ConsultorProvider.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Component
public class CanalClient {

    private Logger logger = LoggerFactory.getLogger(CanalClient.class);
    //sql队列
    private Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();

    @Resource
    private DataSource dataSource;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * canal入库方法
     */
    public void run() {

        //ip需要修改成Linux的ip，端口号固定为11111
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("localhost",
                11111), "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            try {
                while (true) {
                    //尝试从master那边拉去数据batchSize条记录，有多少取多少
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        Thread.sleep(1000);
                    } else {
                        dataHandle(message.getEntries());
                    }
                    connector.ack(batchId);

                    //当队列里面堆积的sql大于一定数值的时候就模拟执行
                    if (SQL_QUEUE.size() >= 1) {
                        executeQueueSql();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        } finally {
            connector.disconnect();
        }
    }



    /**
     * 模拟执行队列里面的sql语句
     */
    public void executeQueueSql() {
        int size = SQL_QUEUE.size();
        for (int i = 0; i < size; i++) {
            String sql = SQL_QUEUE.poll();
            System.err.println("[sql]----> " + sql);
           // this.execute(sql.toString());
        }
    }

    /**
     * 数据处理
     *
     * @param entrys
     */
    private void dataHandle(List<Entry> entrys) throws InvalidProtocolBufferException{
        //对增量数据进行循环处理
        for (Entry entry : entrys) {
            if (EntryType.ROWDATA == entry.getEntryType()) {
                if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                    continue;
                }
                //获取一行的数据
                RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
                //获取其中的操作类型，增删改查等
                EventType eventType = rowChange.getEventType();
                //获取数据库名称
                String dbname = entry.getHeader().getSchemaName();
                if(dbname.equals("sspu_consultor")){
                    try{
                        dealIncrementDataStrategically(entry, rowChange, eventType);
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                    if (eventType == EventType.DELETE) {
                        saveDeleteSql(entry);
                    } else if (eventType == EventType.UPDATE) {
                        saveUpdateSql(entry);
                    } else if (eventType == EventType.INSERT) {
                        saveInsertSql(entry);
                    }
                }
            }
        }
    }

    /**
     * 保存更新语句
     *
     * @param entry
     */
    private void saveUpdateSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> newColumnList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("update " + entry.getHeader().getTableName() + " set ");
                for (int i = 0; i < newColumnList.size(); i++) {
                    sql.append(" " + newColumnList.get(i).getName()
                            + " = '" + newColumnList.get(i).getValue() + "'");
                    if (i != newColumnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" where ");
                List<Column> oldColumnList = rowData.getBeforeColumnsList();
                for (Column column : oldColumnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                SQL_QUEUE.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存删除语句
     *
     * @param entry
     */
    private void saveDeleteSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> columnList = rowData.getBeforeColumnsList();
                StringBuffer sql = new StringBuffer("delete from " + entry.getHeader().getTableName() + " where ");
                for (Column column : columnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                SQL_QUEUE.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存插入语句
     *
     * @param entry
     */
    private void saveInsertSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> columnList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("insert into " + entry.getHeader().getTableName() + " (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append(columnList.get(i).getName());
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(") VALUES (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append("'" + columnList.get(i).getValue() + "'");
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
                SQL_QUEUE.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 入库
     * @param sql
     */
    public void execute(String sql) {
        Connection con = null;
        try {
            if(null == sql) return;
            con = dataSource.getConnection();
            QueryRunner qr = new QueryRunner();
            int row = qr.execute(con, sql);
            System.out.println("update: "+ row);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }


    //增量更新
    private void dealIncrementDataStrategically(Entry entry, RowChange rowChage, EventType eventType) throws UnsupportedEncodingException {
        String templog = String.format("binlog[%s:%s] , name[%s,%s] , eventType : %s",
                entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                eventType);
        logger.info(templog);
        String tableName=entry.getHeader().getTableName();
        //按照行进行遍历
        for (RowData rowData : rowChage.getRowDatasList()) {
            //如果是Delete操作，则删除(del)缓存
            if (eventType == EventType.DELETE) {
                //delRedis(entry.getHeader().getTableName(), rowData.getBeforeColumnsList());
            } else {//其他操作，则更新(set)缓存
                //setRedis(entry.getHeader().getTableName(), rowData.getAfterColumnsList());

                if(tableName.equals("service_nums")){
                    List<Column> newColumnList = rowData.getAfterColumnsList();
                    Column service_id=newColumnList.get(0);
                    Column nums=newColumnList.get(1);
                    System.err.println("数据库监听服务号为:"+service_id.getValue()+" 库存为:"+nums.getValue());
                    redisTemplate.opsForValue().set("watch_service_id:"+service_id.getValue(),nums.getValue(),1000, TimeUnit.MILLISECONDS);
                }

                if(tableName.equals("question")){
                    List<Column> newColumnList = rowData.getAfterColumnsList();
                    //redisTemplate.opsForValue().set("question_id:","",60, TimeUnit.SECONDS);
                }

                if(tableName.equals("answers")){
                    List<Column> columnList = rowData.getAfterColumnsList();
                    Column user_id=columnList.get(0);
                    Column q_id=columnList.get(1);
                    Column ans_content=columnList.get(2);
                    redisTemplate.opsForValue().set("ans_id:"+user_id.getValue()+q_id.getValue(), ans_content.getValue(),60, TimeUnit.SECONDS);
                }
            }
        }
    }
}
