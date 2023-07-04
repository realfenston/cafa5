/**

In this task 3 of project 1 you will design a filter based on the Gaussian (use multiple Gaussian with different centers if neccessary) and apply it to the car image to attenuate the impulse-like bursts in the image. You may reuse existing code from your tasks 1 and 2. 

Hint1: Note that the spatial resolution of the car.png is not a power of 2, i.e., it is not of the form 2^m x 2^n. You should create a new suitable image with padded black pixels by changing code in the constructor of the class P1_3. 

To avoid reverse engineering, we do not provide a sample solution for this task.

**/

import java.time.Instant;
import java.time.Duration;
public class P1_3 {
	public P1_3() {
		//Change this code. 
		Img img = new Img("car.png");
		Instant start = Instant.now();
		filterImage(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void filterImage(Img i) {
		int width = i.imgWidth;
        int height = i.imgHeight;

        // Create a new padded image with black pixels
        int paddedWidth = (int) Math.pow(2, Math.ceil(Math.log(width) / Math.log(2)));
        int paddedHeight = (int) Math.pow(2, Math.ceil(Math.log(height) / Math.log(2)));
        Img paddedImage = new Img(paddedWidth, paddedHeight);

        // Copy the original image to the padded image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                paddedImage.img[y * paddedWidth + x] = i.img[y * width + x];
            }
        }

        Complex[] F = fastFourierTransfrom(paddedImage);

        // Implement the Gaussian filter
        double sigma = 20.0; // Adjust as needed

        Complex[] filteredF = new Complex[paddedWidth * paddedHeight];
        for (int y = 0; y < paddedHeight; y++) {
            for (int x = 0; x < paddedWidth; x++) {
                int centerX = paddedWidth / 2;
                int centerY = paddedHeight / 2;
                double distanceX = x - centerX;
                double distanceY = y - centerY;

                // Apply Gaussian function with different centers
                double coefficient = Math.exp(-(distanceX * distanceX + distanceY * distanceY) / (2 * sigma * sigma));

                filteredF[y * paddedWidth + x] = F[y * paddedWidth + x].mul(coefficient);
            }
        }

        inverseFastFourierTransfrom(filteredF, paddedImage);

        // Copy the filtered result back to the original image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                i.img[y * width + x] = paddedImage.img[y * paddedWidth + x];
            } 
        }
    }

    public Complex[] fastFourierTransfrom(Img i) {
    	//Change this to your code from P1_1
    	Complex[] F = new Complex[i.img.length];
		for (int x = 0; x < i.img.length; x++){
			F[x] = new Complex(x, 0);
			F[x] = new Complex(i.img[x], 0);
		}
		return fft(F);
	}

	public Complex[] fft(Complex[] F){
		int n = F.length; 
		if (n == 1){
			return new Complex[] {F[0]};
		}
		Complex[] even = new Complex[n / 2];
		for (int k = 0; k < n / 2; k++){
			even[k] = F[2 * k];
		}

		Complex[] q = fft(even);

		Complex[] odd = new Complex[n/2];
		for (int k = 0; k < n / 2; k++){
			odd[k] = F[2 * k + 1];
		}
		Complex[] r = fft(odd);

		Complex[] y = new Complex[n];
		for (int k = 0; k < n / 2; k++) {
			double kth = -2 * k * Math.PI / n;
			Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
			wk.mul(r[k]);
			Complex q1 = new Complex(q[k].r, q[k].i);
			q1.plus(wk);
			Complex q2 = new Complex(q[k].r, q[k].i);
			q2.minus(wk);
			y[k * 2] = q1;
			y[k * 2 + 1] = q2;
		}

		return y;
	}

	private void inverseFastFourierTransfrom(Complex[] F, Img i) {
		//Your code here
		int n = F.length;
        Complex[] x = new Complex[n];

        for (int m = 0; m < n; m++) {
            x[m] = new Complex(F[m].r, -F[m].i);
        }
        x = fft(x);

        for (int m = 0; m < n; m++) {
            x[m] = new Complex(x[m].r, -x[m].i);
        }

        for (int m = 0; m < n; m++) {
            x[m].div(1.0 / n);
        }
		return;
	}

	public static void main(String[] args) {
		new P1_3();
	}
}
