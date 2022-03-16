package entity.service_history;

import java.io.Serializable;
import java.util.Date;

public class Service_history implements Serializable {
    private Long his_id;
    private Long user_id;
    private int type;
    private Date time;

    public Long getHis_id() {
        return his_id;
    }

    public void setHis_id(Long his_id) {
        this.his_id = his_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
