package com.f82019.education4all.models;

import com.orm.SugarRecord;

public class Dictionary extends SugarRecord {
    private String word;
    private String description;
    private String example_sentences;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample_sentences() {
        return example_sentences;
    }

    public void setExample_sentences(String example_sentences) {
        this.example_sentences = example_sentences;
    }

    @Override
    public String toString(){
        return word;
    }
}
