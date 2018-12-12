package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/12/13.
 */

public class BeanEditNewsQuestion {
    private String QuestionID;
    private String TypeContent;
    private String Answer;

    public BeanEditNewsQuestion(String questionID, String typeContent, String answer) {
        QuestionID = questionID;
        TypeContent = typeContent;
        Answer = answer;
    }
}
