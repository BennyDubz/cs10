package SA3;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Alex Craig
 *
 * Image Processor program which can apply a blur-brush affect to an image
 */

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */

public class ImageProcessor0 {
	private BufferedImage image;		// the current image being processed

	/**
	 * @param image		the original
	 */
	public ImageProcessor0(BufferedImage image) { this.image = image; }

	/**
	 * Blurs in circle around cursor
	 *
	 * @param cx		center x of blur circle (mouseX)
	 * @param cy		center y of blur circle (mouseY)
	 * @param r		radius of blur circle
	 */
	public void blurBrush(int cx, int cy, int r) {

		// Create a new image into which the resulting pixels will be stored.
		BufferedImage result = createBlankResult();

		// Nested loop over every pixel to check if pixel is in radius of cursor
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {

				// Set local variables for blur calculation
				int sumR = 0; int sumG = 0; int sumB = 0;
				int n = 0;
				int nRadius = 1;

				// Only blur out to specified radius
				double dist = 0.1 * Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy)) / r;
				if (dist <= 1) {
					// Nested loop over neighboring pixels (being careful not to go outside image)
					for (int ny = Math.max(0, y - nRadius); ny < Math.min(image.getHeight(), y + 1 + nRadius); ny++) {
						for (int nx = Math.max(0, x - nRadius); nx < Math.min(image.getWidth(), x + 1 + nRadius); nx++) {
							// Calculate blur with average of neighbors' RGB values
							Color c = new Color(image.getRGB(nx, ny));
							sumR += c.getRed();
							sumG += c.getGreen();
							sumB += c.getBlue();
							n++;
						}
					}
					// Set result pixel RGB to new RGB from average calculation
					Color newColor = new Color(sumR/n, sumG/n, sumB/n);
					result.setRGB(x, y, newColor.getRGB());

				// If pixel not in blur radius, just keep it the same in the result
				} else {
					result.setRGB(x, y, image.getRGB(x, y));
				}
			}
		}

		// Set image to new calculated result
		image = result;
	}

	private BufferedImage createBlankResult() {
		// Create a new image into which the resulting pixels will be stored.
		return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
