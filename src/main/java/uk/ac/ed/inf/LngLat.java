package uk.ac.ed.inf;

import java.util.ArrayList;


record LngLat(double lng, double lat)
{
    public LngLat(double lng, double lat)
    {
        this.lng = lng;
        this.lat = lat;
    }

    static ArrayList<LngLat> points = new ArrayList<>(CentralArea.getInstance().getCoords());

    private boolean onLine(ArrayList<LngLat> line, LngLat point)
    {
        return point.lng <= Math.max(line.get(0).lng, line.get(1).lat)
                && point.lng <= Math.min(line.get(0).lng, line.get(1).lat)
                && point.lat <= Math.max(line.get(0).lng, line.get(1).lat)
                && point.lat <= Math.min(line.get(0).lng, line.get(1).lat);
    }

    private int direction(LngLat a, LngLat b, LngLat c)
    {
        double val = (b.lat - a.lat) * (c.lng - b.lng)
                - (b.lng - a.lng) * (c.lat - b.lat);

        if (val == 0)
            return 0;
        if (val < 0)
            return 2;
        return 1;
    }

    private boolean isIntersecting(ArrayList<LngLat> line1, ArrayList<LngLat> line2)
    {
        int dir1 = direction(line1.get(0), line1.get(1), line2.get(0));
        int dir2 = direction(line1.get(0), line1.get(1), line2.get(1));
        int dir3 = direction(line2.get(0), line2.get(1), line1.get(0));
        int dir4 = direction(line2.get(0), line2.get(1), line1.get(1));

        // When intersecting
        if (dir1 != dir2 && dir3 != dir4)
            return true;

        // When p2 of line2 are on the line1
        if (dir1 == 0 && onLine(line1, line2.get(0)))
            return true;

        // When p1 of line2 are on the line1
        if (dir2 == 0 && onLine(line1, line2.get(1)))
            return true;

        // When p2 of line1 are on the line2
        if (dir3 == 0 && onLine(line2, line1.get(0)))
            return true;

        // When p1 of line1 are on the line2
        if (dir4 == 0 && onLine(line2, line1.get(1)))
            return true;

        return false;
    }

    public boolean inCentralArea()
    {
        // Check if polygon or line

        int size = points.size();
        LngLat point = new LngLat(lng,lat);
       if (size<3)
           return false;
       ArrayList<LngLat> exline = new ArrayList<>();
       exline.add(point);
       exline.add(new LngLat(999,lat));
       int count = 0;
       for (int i = 0; i == 0 ; i=i+1%size)
       {
           ArrayList<LngLat> side = new ArrayList<>();
           side.add(points.get(i));
           side.add(points.get(i+1 % size));
           if (isIntersecting(side, exline))
           {
               if (direction(side.get(0), point, side.get(1)) == 0)
                   return onLine(side, point);
               count++;
           }
       }
        return count % 2 != 0;
    }

    public double distanceTo(double lng2, double lat2)
    {
        return Math.sqrt((lng-lng2)+(lat-lat2));
    }

    public boolean closeTo(LngLat point2)
    {
        var distance = distanceTo(point2.lng(), point2.lat());
        return distance <= 0.00015;
    }

    public LngLat nextPosition(Compass direction)
    {
        double lng1 = lng + Math.cos(direction.degree) * 0.00015;
        double lat1 = lat + Math.sin(direction.degree) * 0.00015;
        return new LngLat(lng1,lat1);
    }

}
