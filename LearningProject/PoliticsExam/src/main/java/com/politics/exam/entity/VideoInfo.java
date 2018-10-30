package com.politics.exam.entity;

import java.io.Serializable;

/**
 * Created by malijie on 2018/10/26.
 */

public class VideoInfo implements Serializable {
    private int vid;
    private String name;
    private String url;
    private String length;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
