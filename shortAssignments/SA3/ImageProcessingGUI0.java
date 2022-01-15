package SA3;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * @author Alex Craig
 *
 * Allowed for a blurBrush method to be called which blurs radius around cursor
 */

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * Load an image and display it
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */
public class ImageProcessingGUI0 extends DrawingGUI {
	private ImageProcessor0 proc; // handles the image processing
	private int radius = 2; // radius of blur effect
	private boolean brush = false; // handles whether brush is up or down

	/**
	 * Creates the GUI for the image processor, with the window scaled to the to-process image's size
	 */
	public ImageProcessingGUI0(ImageProcessor0 proc) {
		super("Image processing", proc.getImage().getWidth(), proc.getImage().getHeight());
		this.proc = proc;
	}

	/**
	 * DrawingGUI method, here showing the current image
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(proc.getImage(), 0, 0, null);
	}

	/**
	 * DrawingGUI method, here dispatching on image processing operations
	 */
	@Override
	public void handleKeyPress(char op) {
		System.out.println("Handling key '"+op+"'");
		if (op=='s') { // save a snapshot
			saveImage(proc.getImage(), "pictures/snapshot.png", "png");
		}
		else if (op=='b') { // Toggle brush with b
			brush = !brush;
		}
		else {
			System.out.println("Unknown operation");
		}

		repaint(); // Re-draw, since image has changed
	}

	@Override
	public void handleMouseMotion(int x, int y) {
		if (brush) {
			proc.blurBrush(x, y, radius);
		}

		repaint();
	}

	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Load the image to process
				BufferedImage baker = loadImage("assets/images/baker.jpeg");
				// Create a new processor, and a GUI to handle it
				new ImageProcessingGUI0(new ImageProcessor0(baker));
			}
		});
	}
}
