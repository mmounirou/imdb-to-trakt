package fr.mmounirou.trakt.api.responses;

/**
 * Created by mmounirou on 01/03/2014.
 */
public class ListDeleteItemsResponse {
    private String status;
    private String message;

    public ListDeleteItemsResponse() {
    }

    public ListDeleteItemsResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
