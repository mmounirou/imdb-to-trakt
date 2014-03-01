package fr.mmounirou.trakt.api.utils;

/**
 * Created by mmounirou on 27/02/14.
 */
public class TraktTypedItem {

    private String type;
    private TraktItem movie;
    private TraktItem show;


    public TraktTypedItem() {
    }

    public static TraktTypedItem buildMovie(TraktItem movie){
        TraktTypedItem traktItem = new TraktTypedItem();
        traktItem.setType("movie");
        traktItem.setMovie(movie);
        return traktItem;
    }

    public static TraktTypedItem buildShow(TraktItem show){
        TraktTypedItem traktItem = new TraktTypedItem();
        traktItem.setType("show");
        traktItem.setShow(show);
        return traktItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TraktItem getMovie() {
        return movie;
    }

    public void setMovie(TraktItem movie) {
        this.movie = movie;
    }

    public TraktItem getShow() {
        return show;
    }

    public void setShow(TraktItem show) {
        this.show = show;
    }
}
