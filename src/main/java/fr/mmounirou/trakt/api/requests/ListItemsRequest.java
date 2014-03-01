package fr.mmounirou.trakt.api.requests;

import fr.mmounirou.trakt.api.utils.TraktItem;

import java.util.List;

public class ListItemsRequest {
    private  String username;
    private  String password;
    private  String slug;
    private List<TraktItem> items;

    public ListItemsRequest(){}

    public ListItemsRequest(String username, String password, String slug, List<TraktItem> items) {
        this.username = username;
        this.password = password;
        this.slug = slug;
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSlug() {
        return slug;
    }

    public List<TraktItem> getItems() {
        return items;
    }
}
