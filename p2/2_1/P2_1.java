/**
  * In this task you will implement the method gradientImage of the class P2_1 which will calculate the gradient image.
  *
  * To determine the gradient in images in x and y directions use the masks [-1 0 1]^T and [-1 0 1], respectively.
  *
  * Note that values should be scaled to [0, 255]. This can be done by multiplying with 1 / sqrt(2). 
  *
  **/

import java.awt.Color;

public class P2_1 {
	public P2_1() {
		Img img = new Img("/root/pretrain/esm/p2/2_1/Fig0314a.png");
		gradientImage(img);
		img.save();
	}
	
	public void gradientImage(Img i) {
		//Your code here
		int width = i.getWidth();
        int height = i.getHeight();

        // Create a new image to store the gradient values
        Img gradientImg = new Img(width, height);

        // Calculate the gradient for each pixel
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // Apply the masks [-1 0 1]^T and [-1 0 1] to calculate gradients in x and y directions
                Color leftPixel = i.getPixel(x - 1, y);
                Color rightPixel = i.getPixel(x + 1, y);
                Color topPixel = i.getPixel(x, y - 1);
                Color bottomPixel = i.getPixel(x, y + 1);

                int rx = rightPixel.getRed() - leftPixel.getRed();
                int gx = rightPixel.getGreen() - leftPixel.getGreen();
                int bx = rightPixel.getBlue() - leftPixel.getBlue();

                int ry = bottomPixel.getRed() - topPixel.getRed();
                int gy = bottomPixel.getGreen() - topPixel.getGreen();
                int by = bottomPixel.getBlue() - topPixel.getBlue();

                // Calculate the magnitude of the gradient using Euclidean distance formula
                double magnitude = Math.sqrt(rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by);

                // Scale the magnitude to [0, 255]
                double scaledMagnitude = magnitude * (1 / Math.sqrt(2));

                // Convert the scaled magnitude to an integer value
                int gradientValue = (int) scaledMagnitude;

                // Create a new color with the same RGB values for the gradient image pixel
                Color gradientColor = new Color(gradientValue, gradientValue, gradientValue);

                // Set the gradient color to the corresponding pixel in the gradient image
                gradientImg.setPixel(x, y, gradientColor);
            }
        }

        // Replace the original image with the gradient image
        i.setData(gradientImg.getData());
	}
		
	public static void main(String[] args) {
		new P2_1();
	}
}
