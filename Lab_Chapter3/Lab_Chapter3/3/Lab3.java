/**

In this task you will implement the method boxFilter of the class Lab3 which applies the box smooth filter on the image i. 

You may handle the boundary case in any way that you see fit. E.g., by keeping the pixels unchanged. 

The expected output is provided in the files solution3.png and solution7.png. 

The solution files are provided for qualitative comparison. Output could be slightly different because of differences in floating point arithmetic. 

**/

import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;
public class Lab3 {
	public Lab3() {
		Img img = new Img("/root/pretrain/esm/Lab_Chapter3/Lab_Chapter3/3/Fig0441.png");
		System.out.print("Size: ");
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		Instant start = Instant.now();
		boxFilter(img, size);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).toMillis()+"ms");
		img.save();
	}
	
	public void boxFilter(Img i, int size) {
		//Your code here
		byte[][] reshaped_image = new byte[i.height][i.width];
		for(int x = 0; x < i.height; x++) {
			for (int y = 0; y < i.width; y++) {
				reshaped_image[x][y] = i.img[x * i.width + y];
			}

		}

		for(int x = 0; x < i.height; x++) {
			for (int y = 0; y < i.width; y++) {
				int left_top = Math.max(0, x - size / 2);
				int right_top = Math.min(i.height - 1, x + size / 2);
				int left_bottom = Math.max(0, y - size / 2);
				int right_bottom = Math.min(i.width - 1, y + size / 2);

				int intensity = 0;
				for (int dim_x = left_top; dim_x <= right_top; dim_x++) {
					for (int dim_y = left_bottom; dim_y <= right_bottom; dim_y++) {
						intensity += reshaped_image[dim_x][dim_y] & 0xff;
					}
				}
				i.img[x * i.width + y] = (byte)(intensity / (size * size));
			}
		}
	}
	
		
	public static void main(String[] args) {
		new Lab3();
	}
}
