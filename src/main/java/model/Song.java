package model;
public class Song {
    private String id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int duration;
    private int playCount;
    private boolean favorite;
    
    public Song(String id, String title, String artist, String album, String genre, int duration){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.playCount = 0;
        this.favorite = false;
    }
    
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getArtist(){
        return artist;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    
    public String getAlbum(){
        return album;
    }
    public void setAlbum(String album){
        this.album = album;
    }
    
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public int getDuration(){
        return duration;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    
    public int getPlayCount(){
        return playCount;
    }
    public void increasePlayCount(){
        this.playCount++;
    }
    
    public boolean isFavorite(){
        return favorite;
    }
    public void toggleFavorite(){
        this.favorite = !this.favorite;
    }
    
    @Override
    public String toString(){
        return id + " | " + title + " - " + artist + " | " + album 
               + " | " + genre + " | " + duration + "s"
               + " | plays: " + playCount 
               + " | fav: " + (favorite ? "yes" : "no");
    }
}