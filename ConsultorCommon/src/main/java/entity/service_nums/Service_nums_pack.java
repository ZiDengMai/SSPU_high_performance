package entity.service_nums;

import java.io.Serializable;
import java.util.List;

public class Service_nums_pack implements Serializable {
    Long service_id;
    Long user_id;
    Long q_id;
    int start;
    List<Integer> ends;

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Integer> getEnds() {
        return ends;
    }

    public void setEnds(List<Integer> ends) {
        this.ends = ends;
    }
}
