package com.a3nlotta.model.draw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HowToPlayData implements Serializable
{

    @SerializedName("how_to_play")
    @Expose
    private List<HowToPlay> howToPlay = null;
    @SerializedName("note")
    @Expose
    private List<HowToPlay> note = null;
    private final static long serialVersionUID = 3959980566210716280L;

    public List<HowToPlay> getHowToPlay() {
        return howToPlay;
    }

    public void setHowToPlay(List<HowToPlay> howToPlay) {
        this.howToPlay = howToPlay;
    }

    public List<HowToPlay> getNote() {
        return note;
    }

    public void setNote(List<HowToPlay> note) {
        this.note = note;
    }

}
