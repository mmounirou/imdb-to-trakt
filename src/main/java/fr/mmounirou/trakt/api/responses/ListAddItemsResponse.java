package fr.mmounirou.trakt.api.responses;

import fr.mmounirou.trakt.api.utils.TraktItem;

import java.util.List;

/**
 * Created by mmounirou on 01/03/2014.
 */
public class ListAddItemsResponse {
    private String status;
    private Integer inserted;
    private Integer already_exist;
    private Integer skipped;
    private List<TraktItem> skipped_items;

    public ListAddItemsResponse() {
    }

    public ListAddItemsResponse(String status, Integer inserted, Integer already_exist, Integer skipped, List<TraktItem> skipped_items) {
        this.status = status;
        this.inserted = inserted;
        this.already_exist = already_exist;
        this.skipped = skipped;
        this.skipped_items = skipped_items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getInserted() {
        return inserted;
    }

    public void setInserted(Integer inserted) {
        this.inserted = inserted;
    }

    public Integer getAlready_exist() {
        return already_exist;
    }

    public void setAlready_exist(Integer already_exist) {
        this.already_exist = already_exist;
    }

    public Integer getSkipped() {
        return skipped;
    }

    public void setSkipped(Integer skipped) {
        this.skipped = skipped;
    }

    public List<TraktItem> getSkipped_items() {
        return skipped_items;
    }

    public void setSkipped_items(List<TraktItem> skipped_items) {
        this.skipped_items = skipped_items;
    }



}
