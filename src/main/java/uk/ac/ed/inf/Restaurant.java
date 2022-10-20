package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Restaurant
{
    @JsonProperty("name")
    public String name;
    @JsonProperty("longitude")
    public double longitude;
    @JsonProperty("latitude")
    public double latitude;
    @JsonProperty("menu")
    public ArrayList<Menu> menu;

    /**
     * Takes a url for the Rest Server, to return an array of the participating restaurants.
     * @param url an absolute URL giving the base location of the JSON data.
     * @return Array of the restaurants from the data at the specified URL.
     * @throws IOException
     */
    public static Restaurant[] getRestaurantsFromRestServer(URL url) throws IOException
    {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(url, Restaurant[].class);
    }
    public ArrayList<Menu> getMenu()
    {
        return menu;
    }
}
