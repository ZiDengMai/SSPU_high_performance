package entity.question;

import entity.course.C_Relation;

import java.io.Serializable;
import java.util.List;

public class SingleQuestion implements Serializable{
    public String type;
    public String title;
    public List<String> selections;
    public String answer;
    public List<C_Relation[]> relations;

    public String getType() {
        return type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public List<C_Relation[]> getRelations() {
        return relations;
    }

    public void setRelations(List<C_Relation[]> relations) {
        this.relations = relations;
    }

}
