
public class GameData {
    private String id;
    private String title;
    private String genre;
    private String developer;
    private String releaseDate;
    private double rating;
    private String imagePath;

    public GameData(String id, String title, String genre, String developer,
                    String releaseDate, double rating, String imagePath) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.imagePath = imagePath;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

}







