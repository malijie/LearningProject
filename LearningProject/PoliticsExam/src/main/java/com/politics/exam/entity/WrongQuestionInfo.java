package com.politics.exam.entity;

import java.io.Serializable;

/**
 * Created by malijie on 2017/6/1.
 */

public class WrongQuestionInfo implements Serializable{
    private int questionId;
    private int chapterParentId;
    private String chapter;
    private String title;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String restore;
    private String explain;
    private String subjectName;
    private String answer;
    private String type;



    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getChapterParentId() {
        return chapterParentId;
    }

    public void setChapterParentId(int chapterParentId) {
        this.chapterParentId = chapterParentId;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapterId(String chapter) {
        this.chapter = chapter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getRestore() {
        return restore;
    }

    public void setRestore(String restore) {
        this.restore = restore;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WrongQuestionInfo{" +
                "questionId=" + questionId +
                ", chapterParentId=" + chapterParentId +
                ", chapter='" + chapter + '\'' +
                ", title='" + title + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", restore='" + restore + '\'' +
                ", explain='" + explain + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", answer='" + answer + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
