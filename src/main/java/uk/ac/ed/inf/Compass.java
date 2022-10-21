package uk.ac.ed.inf;

public enum Compass
{
    /**
     * East Southeast. 337.5 Degrees
     */
    ESE(337.5),
    /**
     * Southeast. 315 Degrees
     */
    SE(315),
    /**
     * South Southeast. 292.5 Degrees
     */
    SSE(292.5),
    /**
     * South. 270 Degrees
     */
    S(270),
    /**
     * South Southwest. 247.5 Degrees
     */
    SSW(247.5),
    /**
     * Southwest. 225 Degrees
     */
    SW(225),
    /**
     * West Southwest. 202.5 Degrees
     */
    WSW(202.5),
    /**
     * West. 180 Degrees
     */
    W(180),
    /**
     * West Northwest. 157.5 Degrees
     */
    WNW(157.5),
    /**
     * Northwest. 135 Degrees
     */
    NW(135),
    /**
     * North Northwest. 112.5 Degrees
     */
    NNW(112.5),
    /**
     * North. 67.5 Degrees
     */
    N(90),
    /**
     * North Northeast. 45 Degrees
     */
    NNE(67.5),
    /**
     * Northeast. 22.5 Degrees
     */
    NE(45),
    /**
     * East Northeast. 0 Degrees
     */
    ENE(22.5),
    /**
     * East. 0 Degrees
     */
    E(0);


    public final double degree;
    private Compass(double degree)
    {
        this.degree=degree;
    }
}
