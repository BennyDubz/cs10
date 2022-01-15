package SA2;

import java.awt.*;

/**
 * Similar to regular wandering blob, except movement is more resolute
 * Also added a color instance variable which determines the color of the blob
 *
 * @author Alex Craig
 */

public class EnhancedWanderingPixel extends Wanderer {
    private int numStepsBetweenVelChange = (int)(4 + Math.random() * 6);
    private int numStepsSinceVelChange = 0;
    private Color color;

    public EnhancedWanderingPixel(double x, double y) {
        super(x, y);
    }

    public EnhancedWanderingPixel(double x, double y, double r, Color color) {
        super(x, y, r);
        this.color = color;
    }

    @Override
    public void step() {
        if (numStepsSinceVelChange < numStepsBetweenVelChange) {
            x += dx;
            y += dy;
            numStepsSinceVelChange += 1;
        } else {
            super.step();
            numStepsSinceVelChange = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
    }

    public Color getColor() {
        return this.color;
    }

}
