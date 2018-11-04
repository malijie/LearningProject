package com.pay.lib.pay;

/**
 * Created by malijie on 2018/11/3.
 */

public interface IPayType {
    int POLITICS_QUESTION = 1;
    int POLITICS_VIDEO = 2;

    void payForPoliticsVideo();
    void payForPoliticsQuestions();
}
