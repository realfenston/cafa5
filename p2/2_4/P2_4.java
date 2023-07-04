/**
  * In this task you will implement the method detectCorners of the class P2_4 which will implement the Harris Corner Detection Algorithm, return a list of detected corners (up to sub pixel accuracy) and sets pixels at corners to 255 and all others to 0. 
  * 
  * 
  **/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
public class P2_4 {
	public P2_4() { 
		Img img = new Img("chessmat.png");
		System.out.print("Sigma: ");
		Scanner in = new Scanner(System.in);
		double s = in.nextDouble();
		System.out.print("Threshold: ");
		double t = in.nextDouble();
		ArrayList<double[]> corners = detectCorners(img, s, t);
		System.out.println("Corners:");
		for (double[] corner : corners) {
			System.out.println(corner[0]+", "+corner[1]);
		}
		img.save();
	}

	public ArrayList<double[]> detectCorners(Img i, double sigma, double threshold) {
		//Your code here
		ArrayList<double[]> cornersOut = new ArrayList<double[]>();
		return cornersOut;
	}

	public static void main(String[] args) {
		new P2_4();
	}
}
