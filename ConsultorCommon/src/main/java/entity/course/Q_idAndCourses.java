package entity.course;

import java.util.List;

public class Q_idAndCourses {
    private Long Q_id;
    private List<Course> courses;

    public Long getQ_id() {
        return Q_id;
    }

    public void setQ_id(Long q_id) {
        Q_id = q_id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
