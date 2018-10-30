package com.politics.exam.entity;

/**
 * Created by malijie on 2017/6/5.
 */

public class OptionInfo {
    private int questionId;
    private String value;
    private String key;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "OptionInfo{" +
                "questionId=" + questionId +
                ", value='" + value + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
