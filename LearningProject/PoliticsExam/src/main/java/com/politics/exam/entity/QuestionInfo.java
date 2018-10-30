package com.politics.exam.entity;

import java.io.Serializable;

/**
 * Created by malijie on 2017/6/1.
 */

public class QuestionInfo implements Serializable{
    private int questionId;
    private int chapterParentId;
    private int chapterId;
    private String title;
    private String titleImage;
    private int sNum;
    private String unit;
    private String number;
    private String year;
    private int numberNumber;
    private String numberType;
    private String restore;
    private String explain;
    private String mediaUrl;
    private String mediaImage;
    private String subjectName;
    private String mediaId;
    private String type;
    private String answer;


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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNumberNumber() {
        return numberNumber;
    }

    public void setNumberNumber(int numberNumber) {
        this.numberNumber = numberNumber;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
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

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(String mediaImage) {
        this.mediaImage = mediaImage;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "questionId=" + questionId +
                ", chapterParentId=" + chapterParentId +
                ", chapterId=" + chapterId +
                ", title='" + title + '\'' +
                ", titleImage='" + titleImage + '\'' +
                ", sNum=" + sNum +
                ", unit='" + unit + '\'' +
                ", number='" + number + '\'' +
                ", year='" + year + '\'' +
                ", numberNumber=" + numberNumber +
                ", numberType='" + numberType + '\'' +
                ", restore='" + restore + '\'' +
                ", explain='" + explain + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", mediaImage='" + mediaImage + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", type='" + type + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
