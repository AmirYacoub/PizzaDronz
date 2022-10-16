package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class OrderClient
{
    public static void main(String[] args ) {
        if (args.length != 2){
            System.err.println("Testclient https://ilp-rest.azurewebsites.net/ test");
            System.err.println("you must supply the base address of the ILP REST Service\n" +
                    " e.g. http://restservice.somewhere and a string to be echoed"); System. exit (1);
        }
        try {
            String baseUrl = args [0];
            String path = args [1];
            if (! baseUrl.endsWith("/")){ baseUrl += "/";
            }
            // we call the test endpoint and pass in some test data which will be echoed
            URL url = new URL(baseUrl + path);
            /*
             * the Jackson JSON library provides helper methods which can directly
             * take a URL, perform the GET request convert the result to the specified class
             */
            Order order = new ObjectMapper().readValue(new URL(baseUrl + path) , Order.class );

        }
        catch ( IOException e )
        {
            e . printStackTrace ( ) ;
        }
    }
}
