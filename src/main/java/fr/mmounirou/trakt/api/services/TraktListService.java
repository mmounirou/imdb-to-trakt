package fr.mmounirou.trakt.api.services;

import fr.mmounirou.trakt.api.requests.ListItemsRequest;
import fr.mmounirou.trakt.api.responses.ListAddItemsResponse;
import fr.mmounirou.trakt.api.responses.ListDeleteItemsResponse;
import fr.mmounirou.trakt.api.responses.ListGetItemsResponse;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by mmounirou on 27/02/14.
 */
public interface TraktListService {

    @POST("/lists/items/add/{apikey}")
    public ListAddItemsResponse addItems(@Path("apikey") String apiKey,@Body ListItemsRequest request);

    @POST("/lists/items/delete/{apikey}")
    public ListDeleteItemsResponse deleteItems(@Path("apikey") String apiKey,@Body ListItemsRequest request);


    @GET("/user/list.json/{apikey}/{username}/{slug}")
    public ListGetItemsResponse getItems(@Path("apikey") String apiKey,@Path("username") String username,@Path("slug") String slug);
}
