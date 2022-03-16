package entity.answers;

import entity.course.C_Relation;
import entity.course.Course;

import java.util.List;

public class SingleAnswer {
    public String type;
    public String title;
    public List<String> selections;
    public List<String> answer;
    public List<Course> relations;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSelections() {
        return selections;
    }

    public void setSelections(List<String> selections) {
        this.selections = selections;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public List<Course> getRelations() {
        return relations;
    }

    public void setRelations(List<Course> relations) {
        this.relations = relations;
    }
}
