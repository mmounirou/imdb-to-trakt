package fr.mmounirou.trakt.api.utils;

/**
 * Created by mmounirou on 27/02/14.
 */
public class TraktRatings {
    private Integer percentage;
    private Integer votes;
    private Integer loved;
    private Integer hated;

    public TraktRatings() {
    }

    public TraktRatings(Integer percentage, Integer votes, Integer loved, Integer hated) {
        this.percentage = percentage;
        this.votes = votes;
        this.loved = loved;
        this.hated = hated;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getLoved() {
        return loved;
    }

    public void setLoved(Integer loved) {
        this.loved = loved;
    }

    public Integer getHated() {
        return hated;
    }

    public void setHated(Integer hated) {
        this.hated = hated;
    }
}
