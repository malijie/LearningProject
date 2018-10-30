package com.politics.exam.entity;

/**
 * Created by malijie on 2017/8/23.
 */

public class ExamInfo {

    private int id;
    private String type;
    private String content;
    private String option;
    private String key;
    private String explain;
    private int paperId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Override
    public String toString() {
        return "ExamInfo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", option='" + option + '\'' +
                ", key='" + key + '\'' +
                ", explain='" + explain + '\'' +
                ", paperId=" + paperId +
                '}';
    }
}
