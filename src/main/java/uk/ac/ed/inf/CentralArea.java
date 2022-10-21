package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * Singleton, as only one Central Area exists,
 */
public class CentralArea
{
    private static CentralArea instance;
    private ArrayList<LngLat> coords = new ArrayList<>();

    private CentralArea()
    {

    }

    public static CentralArea getInstance()
    {
        if (instance == null)
        {
            instance = new CentralArea();
        }
        return instance;
    }

    public void setCoords()
    {
        for (int i = 0; i < Client.caArray.size(); i++)
        {
            coords.add(new LngLat(Client.caArray.get(i).longitude, Client.caArray.get(i).latitude));
        }
    }

    public ArrayList<LngLat> getCoords()
    {
        return coords;
    }
}
