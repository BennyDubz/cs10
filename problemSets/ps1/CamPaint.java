package ps1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import javax.swing.*;

/**
 * Webcam-based drawing 
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 * @author Alex Craig, Winter 2022
 * @author Ben Williams, Winter 2022
 */
public class CamPaint extends Webcam {
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private RegionFinder finder;			// handles the finding
	private Region brush; 					// the region which will act as the brush
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color brushColor;				// the color to put into the painting from the "brush"
	private boolean brushDown;				// boolean to determine whether brush is down

	private BufferedImage brushStrokes;		// the image which holds the rgb values of pixels of all brush strokes
	private BufferedImage brushed;			// defines what pixels have been brushed (necessary in case rgb of brush is 0)
	private BufferedImage painting;			// the resulting masterpiece

	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaint() {
		brushStrokes = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		brushed = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		finder = new RegionFinder();
		brushDown = true;
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		// Clear painting and brushes
		brushed = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		brushStrokes = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	}

	/**
	 * DrawingGUI method, here drawing one of live webcam, recolored image, or painting, 
	 * depending on display variable ('w', 'r', or 'p')
	 */
	@Override
	public void draw(Graphics g) throws Exception {
		// TODO: YOUR CODE HERE
		if (displayMode == 'w') {
			g.drawImage(super.image, 0, 0, null);
		}
		if (displayMode == 'p') {
			g.drawImage(this.painting, 0, 0, null);
		}
		if (displayMode == 's') {
			g.drawImage(this.brushStrokes, 0, 0, null);
		}
		if (displayMode == 'r') {
			g.drawImage(finder.getRecoloredImage(), 0, 0, null);
		}
	}

	/**
	 * Webcam method, here finding regions and updating the painting.
	 */
	@Override
	public void processImage() throws Exception {
		// TODO: YOUR CODE HERE
		// Update the finder's image to current webcam output
		finder.setImage(super.image);

		System.out.println(finder.getImage());

		// Look for regions of target color (if one has been set), and set the largest one to brush
		if (targetColor != null) {
			// TODO: this seems to be looking through brush strokes as well
			finder.findRegions(targetColor);
			brush = finder.largestRegion();
		}

		// Brushing loop (adds brush region to brushStokes)
		if (brushDown && brush != null) {
			// Loop through all pixels in brush
			for (int i = 0; i < brush.getPoints().size(); i++) {
				// For every point in brush, set the rgb value to brushColor in brushStrokes
				Point p = brush.getPoint(i);
				brushStrokes.setRGB(p.x, p.y, brushColor.getRGB());
				brushed.setRGB(p.x, p.y, 1);
			}
		}

		// Painting loop (combines brushStrokes with the webcam output to get painting)
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// If the current pixel has been brushed, set painting's rgb value to whatever it is in brushStrokes, otherwise just set it to the webcam output
				if (brushed.getRGB(x, y) == 1) {
					painting.setRGB(x, y, brushStrokes.getRGB(x, y));
				} else {
					painting.setRGB(x, y, image.getRGB(x, y));
				}
			}
		}
	}

	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		// TODO: YOUR CODE HERE
		// Set target color and brush color to whichever color we clicked
		if (image != null) {
			targetColor = new Color(image.getRGB(x, y));
			System.out.println("tracking: " + targetColor);
			if (brush != null) {
				brush.wipeRegion();
			}
			brushColor = targetColor;
		}
	}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w' || k == 's') { // display: painting, recolored image, webcam, or brushStrokes
			displayMode = k;
			System.out.println("Display Mode: " + k);
		}
		else if (k == 'c') { // clear
			clearPainting();
		}
		else if (k == 'b') { // Toggle brush
			brushDown = !brushDown;
			System.out.println("Brush-Down is: " + brushDown);
		}
		else if (k == 'o') { // save the recolored image
			saveImage(finder.getRecoloredImage(), "assets/images/recolored.jpeg", "png");
		}
		else if (k == 's') { // save the painting
			saveImage(painting, "assets/images/baker.jpeg", "png");
		}
		else {
			System.out.println("unexpected key " + k);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CamPaint();
			}
		});
	}
}
