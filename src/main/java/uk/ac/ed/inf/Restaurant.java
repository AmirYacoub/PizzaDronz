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
