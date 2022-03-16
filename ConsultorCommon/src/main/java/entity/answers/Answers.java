package entity.answers;

import java.io.Serializable;

public class Answers implements Serializable {
    private Long user_id;
    private Long q_id;
    private String ans_content;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getQ_id() {
        return q_id;
    }

    public void setQ_id(Long q_id) {
        this.q_id = q_id;
    }

    public String getAns_content() {
        return ans_content;
    }

    public void setAns_content(String ans_content) {
        this.ans_content = ans_content;
    }
}
