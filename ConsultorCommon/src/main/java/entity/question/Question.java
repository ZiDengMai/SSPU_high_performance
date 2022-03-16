package entity.question;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
    private Long user_id;
    private Long q_id;
    private String q_content;
    private String courses;
    private String c_relations;
    private String q_name;
    private String q_script;
    private Integer q_situation;
    private Date q_start_time;
    private Date q_end_time;
    private int route_start;
    private String route_ends;

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

    public String getQ_content() {
        return q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getC_relations() {
        return c_relations;
    }

    public void setC_relations(String c_relations) {
        this.c_relations = c_relations;
    }

    public String getQ_name() {
        return q_name;
    }

    public void setQ_name(String q_name) {
        this.q_name = q_name;
    }

    public String getQ_script() {
        return q_script;
    }

    public void setQ_script(String q_script) {
        this.q_script = q_script;
    }

    public Integer getQ_situation() {
        return q_situation;
    }

    public void setQ_situation(Integer q_situation) {
        this.q_situation = q_situation;
    }

    public Date getQ_start_time() {
        return q_start_time;
    }

    public void setQ_start_time(Date q_start_time) {
        this.q_start_time = q_start_time;
    }

    public Date getQ_end_time() {
        return q_end_time;
    }

    public void setQ_end_time(Date q_end_time) {
        this.q_end_time = q_end_time;
    }

    public int getRoute_start() {
        return route_start;
    }

    public void setRoute_start(int route_start) {
        this.route_start = route_start;
    }

    public String getRoute_ends() {
        return route_ends;
    }

    public void setRoute_ends(String route_ends) {
        this.route_ends = route_ends;
    }
}
