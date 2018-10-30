package com.politics.exam.business;

import java.util.List;

/**
 * Created by malijie on 2017/7/27.
 */

public interface ISelectionMethod {
    void choice(String option);
    void clearData();
    String getSelectionType();
    void checkAnswers(String answer);
    String getSelection();
    void saveAnswers(int id,String options);
}
