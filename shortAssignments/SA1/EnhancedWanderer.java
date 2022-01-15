package SA1;

/**
 * Similar to regular wanderer blob, except movement is more resolute
 *
 * @author Alex Craig
 */

public class EnhancedWanderer extends Wanderer {
    private int numStepsBetweenVelChange;
    private int numStepsSinceVelChange = 0;

    public EnhancedWanderer (double x, double y) {
        super(x, y);
        this.numStepsBetweenVelChange = (int)(50 + Math.random() * 6);
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

}
