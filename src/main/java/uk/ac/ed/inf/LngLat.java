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

    /**
     *  Method checks if a point provided is on a specified line.
     * @param line ArrayList containing 2 elements that would "draw" the line to check.
     * @param point Point to be checked if its position lies on the line.
     * @return Whether point lies on the line or not.
     */
    private boolean onLine(ArrayList<LngLat> line, LngLat point)
    {
        return point.lng <= Math.max(line.get(0).lng, line.get(1).lat)
                && point.lng <= Math.min(line.get(0).lng, line.get(1).lat)
                && point.lat <= Math.max(line.get(0).lng, line.get(1).lat)
                && point.lat <= Math.min(line.get(0).lng, line.get(1).lat);
    }

    /**
     * Takes 3 points and check in which direction the points lie if travelling down a line taken in order from a to c.
     * @param a First point,
     * @param b Second point.
     * @param c Third point.
     * @return Values 0,1 or 2, if direction is co-linear, clockwise, or anti-clockwise, respectively.
     */
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

    /**
     *  Checks if two lines are intersecting each other.
     * @param line1 First Line.
     * @param line2 Second Line.
     * @return Whether lines intersect each other or not.
     */
    private boolean isIntersecting(ArrayList<LngLat> line1, ArrayList<LngLat> line2)
    {
        // Four direction for two lines and points of other line
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

    /**
     * Checks if the current position of the drone lies inside the central area or not.
     * If the central area is a polygon, the point should intersect the polygon an odd number of times.
     * @return Whether point is inside the central area or not.
     */
    public boolean inCentralArea()
    {
       int size = points.size();
       LngLat point = new LngLat(lng,lat);

       //Checks if central area is a polygon or not.
       if (size<3)
           return false;

       //Create a line to "infinity" on the same y-axis.
       ArrayList<LngLat> exline = new ArrayList<>();
       exline.add(point);
       exline.add(new LngLat(999,lat));

       int count = 0;
       for (int i = 0; i == 0 ; i=i+1%size)
       {
           //Forming a line from two consecutive points of the polygon.
           ArrayList<LngLat> side = new ArrayList<>();
           side.add(points.get(i));
           side.add(points.get(i+1 % size));
           if (isIntersecting(side, exline))
           {
               // If side is intersects exline.
               if (direction(side.get(0), point, side.get(1)) == 0)
                   return onLine(side, point);
               count++;
           }
       }
       // Returns true if count is odd.
        return count % 2 != 0;
    }


    /**
     * Calculates the distance from the drones current point to a provided point.
     * @param point2 The point to calculate the distance to.
     * @return The distance between the drones current position and the specified point.
     */
    public double distanceTo(LngLat point2)
    {
        return Math.sqrt((lng- point2.lng)+(lat- point2.lat));
    }

    /**
     * Checks if the drones current position is less than 0.00015 degrees from a second point.
     * @param point2 The point to check if drone is close to or not.
     * @return True if distance is less than or equal to 0.00015 degrees.
     */
    public boolean closeTo(LngLat point2)
    {
        var distance = distanceTo(point2);
        return distance <= 0.00015;
    }

    /**
     * Calculates the next position of the drone based on direction.
     * @param direction The direction the drone should go in based on a 16 point compass.
     * @return The next position of the drone, assuming the move is valid.
     */
    public LngLat nextPosition(Compass direction)
    {
        double lng1 = lng + Math.cos(direction.degree) * 0.00015;
        double lat1 = lat + Math.sin(direction.degree) * 0.00015;
        return new LngLat(lng1,lat1);
    }

}
