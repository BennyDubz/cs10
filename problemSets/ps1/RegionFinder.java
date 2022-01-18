package ps1;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 *
 * @author Alex Craig
 * @author Ben Williams
 */
public class RegionFinder {
	private static final int maxColorDiff = 20;				// how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50; 				// how many points in a region to be worth considering
	private ArrayList<Region> regions;						// a region is a list of point
	private Region region;									// Working as a temporary regin that we fill in, append to regions, then erase

	private BufferedImage image;                            // the image in which to find regions
	private BufferedImage visited;							// tracks pixels which have been visited
	private BufferedImage recoloredImage;                   // the image with identified regions recolored

	public RegionFinder() {
		this.image = null;
	}

	public RegionFinder(BufferedImage image) {
		this.image = image;
		visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		regions = new ArrayList<>();
		region = new Region();
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public BufferedImage getRecoloredImage() {
		return recoloredImage;
	}

	/**
	 * Looks through image and starts each region
	 */
	public void findRegionStarts(Color targetColor) {
		// Loops through all points in the image
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x ++) {
				// Only check pixels which haven't been visited
				if (visited.getRGB(x, y) == 0) {
					Color checkingColor = new Color(image.getRGB(x, y));
					Point checkingPoint = new Point(x,y);

					if (colorMatch(checkingColor, targetColor)) {
						// When we find the start of the region, call the recursive checkNeighbors function
						region.addPoint(checkingPoint);
						checkNeighbors(checkingPoint, targetColor, 1);

						System.out.println(region.getSize());

						// If the completed region is off sufficient size, add it to the regions ArrayList
						if (region.getSize() > minRegion) {
							System.out.println("adding a new region");
							regions.add(region);
							region.wipeRegion();
						}
					}
				}
			}
		}
	}


	public void checkNeighbors(Point center, Color targetColor, int r) {
		// Check square around center point
		System.out.println(center);
		for (int y = Math.max(0, center.y - r); y <= Math.min(image.getHeight(), center.y + r); y++) {
			for (int x = Math.max(0, center.x - r); x <= Math.min(image.getWidth(), center.x + r); x++) {
				// If the pixel hasn't already been visited, then check to see if it is close to our targetColor
				if (visited.getRGB(x, y) == 0) {
					System.out.println("\n\nx: " + x + "\ny: " + y);
					// Add current pixel to visited pixels
					visited.setRGB(x, y, 1);
					Color c = new Color(image.getRGB(x, y));
					if (colorMatch(c, targetColor)) {
						// If the pixel we're checking matched our targetColor, then append it to region and recursively check its neighbors
						Point checkingPoint = new Point(x, y);
						region.addPoint(checkingPoint);
						// checkNeighbors(checkingPoint, targetColor, 1);
					}
				}
			}
		}
	}
	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
	 * @param c1
	 * @param c2
	 */
	private static boolean colorMatch(Color c1, Color c2) {
		// TODO: YOUR CODE HERE
		// Use a squaring function to get a difference between two parameter colors
		int diff = (c1.getRed() - c2.getRed()) * (c1.getRed() - c2.getRed())
				+ (c1.getGreen() - c2.getGreen()) * (c1.getGreen() - c2.getGreen())
				+ (c1.getBlue() - c2.getBlue()) * (c1.getBlue() - c2.getBlue());

		// If the difference is less than maximum acceptable color difference, return true
		return diff <= maxColorDiff;
	}

	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public Region largestRegion() {

		Region largestReg = new Region();
		for (Region reg : regions) {
			if (reg.getSize() > largestReg.getSize()) {
				largestReg = reg;
			}
		}
		return largestReg;
	}

	/**
	 * Sets recoloredImage to be a copy of image, 
	 * but with each region a uniform random color, 
	 * so we can see where they are
	 */
	public void recolorImage() {
		// First copy the original
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// Now recolor the regions in it
		// TODO: YOUR CODE HERE
		for (Region reg : regions) {
			int v = (int)(Math.random() * 16777217);
			for (Point p : reg.getPoints()) {
				recoloredImage.setRGB(p.x, p.y, v);
			}
		}


	}
}
