/**

In this task you will implement the method histogramEqualization of the class Lab2 which will perform histogram equalization.  

The expected output is provided in the files solution1.png and solution2.png.

**/

import java.util.Scanner;
public class Lab2 {
	public Lab2() {
		Img img = new Img("/root/pretrain/esm/Lab_Chapter3/Lab_Chapter3/2/Fig03161.png");
		histogramEqualization(img);
		img.save("out1.png");
		img = new Img("HawkesBay.png");
		histogramEqualization(img);
		img.save("out2.png");
	}

	public void histogramEqualization(Img i) {
		//Your code here
		int[] histogram = new int[256];
		int[] cumsum = new int[256];
		for (int x = 0; x < i.height; x++) {
			for (int y = 0; y < i.width; y++) {
				int intensity = (i.img[x * i.width + y] & 0xff);
				histogram[intensity]++;
			}
		}

		for (int m = 0; m < 256; m++) {
			cumsum[m] = histogram[m];
			if (m != 0){
				cumsum[m] += cumsum[m - 1];
			}
		}

		for (int x = 0; x < i.height; x++) {
			for (int y = 0; y < i.width; y++) {
				int intensity = (i.img[x * i.width + y] & 0xff);
				int newIntensity = (int)(255.0 * cumsum[intensity] / (i.height * i.width));
				i.img[x * i.width + y] = (byte)newIntensity;
			}
		}
	}
	
	public static void main(String[] args) {
		new Lab2();
	}
}
