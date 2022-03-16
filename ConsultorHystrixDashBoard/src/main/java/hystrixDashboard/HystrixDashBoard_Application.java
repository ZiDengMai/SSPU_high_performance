package hystrixDashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashBoard_Application {
    public static void main(String args[]){
        SpringApplication.run(HystrixDashBoard_Application.class,args);
    }
}
