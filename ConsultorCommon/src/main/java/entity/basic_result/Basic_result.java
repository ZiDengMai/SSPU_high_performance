package entity.basic_result;

public class Basic_result {
    private Long user_id;
    private Long q_id;
    private String res_basic;
    private String basic_relation;
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

    public String getRes_basic() {
        return res_basic;
    }

    public void setRes_basic(String res_basic) {
        this.res_basic = res_basic;
    }
    public String getBasic_relation() {
        return basic_relation;
    }

    public void setBasic_relation(String basic_relation) {
        this.basic_relation = basic_relation;
    }
}
