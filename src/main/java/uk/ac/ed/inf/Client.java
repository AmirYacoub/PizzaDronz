package uk.ac.ed.inf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple client to GET JSON data from a remote server
 */
public class Client
{
    public static ArrayList<CACoords> caArray;

    public static void main(String[] args ) {
        try {
            String centralAreaUrl = "https://ilp-rest.azurewebsites.net/centralArea";

            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            caArray = om.readValue(new URL(centralAreaUrl) , new TypeReference<ArrayList<CACoords>>() {});
            CentralArea.getInstance().setCoords();
        }
        catch ( IOException e )
        {
            e . printStackTrace ( ) ;
        }
    }
}
