package entity.user;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long user_id;
    private String user_name;
    private String user_password;
    private Date user_expired;
    private String user_test;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Date getUser_expired() {
        return user_expired;
    }

    public void setUser_expired(Date user_expired) {
        this.user_expired = user_expired;
    }

    public String getUser_test() {
        return user_test;
    }

    public void setUser_test(String user_test) {
        this.user_test = user_test;
    }
}
