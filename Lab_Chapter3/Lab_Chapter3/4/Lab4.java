/**

In this task you will implement the method medianFilter of the class Lab4 which applies the median filter on the image. 

You should handle the boundary case by keeping the pixels unchanged. 

The expected output is provided in the files solution3.png and solution7.png.

**/

import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;
import java.util.Arrays;
public class Lab4 {
	public Lab4() {
		Img img = new Img("Fig0441.png");
		System.out.print("Size: ");
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		Instant start = Instant.now();
		medianFilter(img, size);
		Instant stop = Instant.now();
		//System.out.println("Elapsed time: "+Duration.between(start, stop).toMillis()+"ms");
		img.save();
	}

	public void medianFilter(Img i, int size) {
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

				int[] intensity = new int[size * size];
				for (int dim_x = left_top; dim_x <= right_top; dim_x++) {
					for (int dim_y = left_bottom; dim_y <= right_bottom; dim_y++) {
						intensity[(dim_x-left_top) * size + dim_y-left_bottom] = reshaped_image[dim_x][dim_y] & 0xff;
					}
				}

				Arrays.sort(intensity);
				i.img[x * i.width + y] = (byte)intensity[intensity.length / 2];
			}
		}
	}
		
	public static void main(String[] args) {
		new Lab4();
	}
}
