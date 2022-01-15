package SA1;

/**
 * @author Alex Craig
 *
 * Blob that orbits a point at a specified radius
 */

public class Orbiter extends Blob {
    protected int centerX, centerY;
    protected int x, y;
    protected int circleRadius, radius;
    protected boolean direction;

    public Orbiter (int x, int y, int r, int circleR) {
        centerX = x;
        centerY = y;
        radius = r;
        circleRadius = circleR;

        double rand = Math.random();
        if (rand < .5) { direction = true; } else { direction = false; }

        if (direction) { x =  centerX + circleRadius; } else { x = centerX - circleRadius; }

        y = centerY;
    }

    @Override
    public void step() {

    }

}
