package model;

public class Action {
    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";

    private String type;
    private Song song;   

    public Action(String type, Song song) {
        this.type = type;
        this.song = song;
    }

    public String getType() { return type; }
    public Song getSong() { return song; }
}