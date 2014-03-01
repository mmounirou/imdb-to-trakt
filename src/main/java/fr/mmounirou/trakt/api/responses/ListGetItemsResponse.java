package fr.mmounirou.trakt.api.responses;

import fr.mmounirou.trakt.api.utils.TraktTypedItem;

import java.util.List;

/**
 * Created by mmounirou on 27/02/14.
 */
public class ListGetItemsResponse {
    private String name;
    private String slug;
    private String url;
    private String description;
    private String privacy;
    private boolean show_numbers;
    private boolean allow_shouts;
    private List<TraktTypedItem> items;

    public ListGetItemsResponse() {
    }

    public ListGetItemsResponse(String name, String slug, String url, String description, String privacy, boolean show_numbers, boolean allow_shouts, List<TraktTypedItem> items) {
        this.name = name;
        this.slug = slug;
        this.url = url;
        this.description = description;
        this.privacy = privacy;
        this.show_numbers = show_numbers;
        this.allow_shouts = allow_shouts;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public boolean isShow_numbers() {
        return show_numbers;
    }

    public void setShow_numbers(boolean show_numbers) {
        this.show_numbers = show_numbers;
    }

    public boolean isAllow_shouts() {
        return allow_shouts;
    }

    public void setAllow_shouts(boolean allow_shouts) {
        this.allow_shouts = allow_shouts;
    }

    public List<TraktTypedItem> getItems() {
        return items;
    }

    public void setItems(List<TraktTypedItem> items) {
        this.items = items;
    }
}
