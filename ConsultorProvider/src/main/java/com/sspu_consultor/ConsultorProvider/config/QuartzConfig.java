package com.sspu_consultor.ConsultorProvider.config;


import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail printTimeJobDetail(){
        return JobBuilder.newJob(EveryDateUpdateServiceNum.class)//PrintTimeJob我们的业务类
                .withIdentity("DateTimeJob")//可以给该JobDetail起一个id
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .usingJobData("msg", "定时更新service——num表")//关联键值对
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }
    @Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/0 0 0 * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())//关联上述的JobDetail
                .withIdentity("quartzTaskService")//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail for_EverydayRabbitListner(){
        return JobBuilder.newJob(EverydayRabbitListner.class)//PrintTimeJob我们的业务类
                .withIdentity("send message to rabbitmq")//可以给该JobDetail起一个id
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .usingJobData("msg", "定时更新发送rabbitmq信息")//关联键值对
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }
    @Bean
    public Trigger for_EverydayRabbitListner_JobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/0 32 17 * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(for_EverydayRabbitListner())//关联上述的JobDetail
                .withIdentity("everydayRabbitService")//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
