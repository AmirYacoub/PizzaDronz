package uk.ac.ed.inf;

import java.util.ArrayList;

/**
 * <p>
 *     Record responsible for representing a point, and methods responsible for the calculation of next moves
 *     as well as the other methods to be used in the future to check if a move is valid or not.
 * </p>
 * @param lng the longitude of point.
 * @param lat The latitude of point.
 */
record LngLat(double lng, double lat)
{
    public LngLat(double lng, double lat)
    {
        this.lng = lng;
        this.lat = lat;
    }


    /**
     * Checks if point 3 is on the straight line created by joining point 1 and 2
     * @param point1 First point of the line
     * @param point2 Second point of the line
     * @param pointCheck Point to be checked
     * @return Whether pointCheck is lies on the line created by point 1 and point 2
     */
    private boolean onLine(LngLat point1, LngLat point2, LngLat pointCheck)
    {
        return pointCheck.lng <= Math.max(point1.lng, point2.lng)
                && pointCheck.lng >= Math.min(point1.lng, point2.lng)
                && pointCheck.lat <= Math.max(point1.lat, point2.lat)
                && pointCheck.lat >= Math.min(point1.lat, point2.lat);
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
     * Takes 4 points, creating lines A and B from points A1,A2 and B1,B2 respectively, then checks if these lines intersect
     * @param pointA1 First point of line A
     * @param pointA2 Second point of line A
     * @param pointB1 First point of line B
     * @param pointB2 Second point of line B
     * @return whether lines A and B intersect or not.
     */
    private boolean isIntersecting(LngLat pointA1, LngLat pointA2, LngLat pointB1, LngLat pointB2)
    {
        // Four direction for two lines and points of other line
        int dir1 = direction(pointA1, pointA2, pointB1);
        int dir2 = direction(pointA1, pointA2, pointB2);
        int dir3 = direction(pointB1, pointB2, pointA1);
        int dir4 = direction(pointB1, pointB2, pointA2);

        // When intersecting
        if (dir1 != dir2 && dir3 != dir4)
            return true;

        if (dir1 == 0 && onLine(pointA1, pointA2, pointB1))
            return true;

        if (dir2 == 0 && onLine(pointA1, pointA2, pointB2))
            return true;

        if (dir3 == 0 && onLine(pointB1,pointB2, pointA1))
            return true;

        return dir4 == 0 && onLine(pointB1, pointB2, pointA2);
    }

    /**
     * Checks if the current position of the drone lies inside the central area or not.
     * If the central area is a polygon, the point should intersect the polygon an odd number of times.
     * @return Whether point is inside the central area or not.
     */
    public boolean inCentralArea()
    {
        ArrayList<LngLat> centralArea = new ArrayList<>(CentralArea.getInstance().getCoords());
       int size = centralArea.size();
       LngLat point = new LngLat(lng,lat);

       //Checks if central area is a polygon or not.
       if (size<3)
           return false;

       //Create a point at an x-extreme on the same y-axis.
       LngLat extreme = new LngLat(5,lat);

       int count = 0;
       int i = 0;
       do
       {
           int next = (i+1)%size;
           //Forming a line from two consecutive points of the polygon.
           if (isIntersecting(centralArea.get(i), centralArea.get(next), point, extreme))
           {
               // If side is intersects exline.
               if (direction(centralArea.get(i), centralArea.get(next), point) == 0){
                   return onLine(centralArea.get(i), centralArea.get(next), point);
               }
               count++;
           }
           i = next;
       }while (i!=0);

       // Returns true if count is odd.
        return (count & 1) == 1;
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
