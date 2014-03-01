package fr.mmounirou.trakt.api.utils;

import java.util.List;

/**
 * Created by mmounirou on 27/02/14.
 */
public class TraktItem {
    private String type;
    private String title;
    private Integer year;
    private Integer released;
    private String url;
    private String trailer;
    private Integer runtime;
    private String tagline;
    private String overview;
    private String certification;
    private String imdb_id;
    private String tmdb_id;
    private TraktImages images;
    private TraktRatings ratings;
    private List<String> genres;

    public TraktItem() {
    }

    public TraktItem(String type, String title, Integer year, Integer released, String url, String trailer, Integer runtime, String tagline, String overview, String certification, String imdb_id, String tmdb_id, TraktImages images, TraktRatings ratings, List<String> genres) {
        this.type = type;
        this.title = title;
        this.year = year;
        this.released = released;
        this.url = url;
        this.trailer = trailer;
        this.runtime = runtime;
        this.tagline = tagline;
        this.overview = overview;
        this.certification = certification;
        this.imdb_id = imdb_id;
        this.tmdb_id = tmdb_id;
        this.images = images;
        this.ratings = ratings;
        this.genres = genres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public TraktImages getImages() {
        return images;
    }

    public void setImages(TraktImages images) {
        this.images = images;
    }

    public TraktRatings getRatings() {
        return ratings;
    }

    public void setRatings(TraktRatings ratings) {
        this.ratings = ratings;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
