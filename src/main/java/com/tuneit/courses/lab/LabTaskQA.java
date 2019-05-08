package com.tuneit.courses.lab;

import org.apache.commons.collections4.map.LRUMap;

/**
 * Dummy Class to persisting store already generated task question and answer
 * and avoid high load data corruption.
 *
 * @author serge
 */

public class LabTaskQA {

    //cache for store question and md5 for correct answer
    private static LRUMap<String, LabTaskQA> cache = new LRUMap<>(100000);
    protected final String id;
    protected final String question;
    protected final String correctAnswer;
    protected String correctCheckSum = "";

    public LabTaskQA(final String id, final String question, final String correctAnswer) {
        if (id == null || question == null || correctAnswer == null) {
            throw new IllegalArgumentException("Could not instantiate LabTaskQA with null values in constructor");
        }
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCorrectCheckSum() {
        return correctCheckSum;
    }

    public void setCorrectCheckSum(String correctCheckSum) {
        this.correctCheckSum = correctCheckSum;
    }


}