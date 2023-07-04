/**

In this task 2 of project 1 you will implement the inverse fast Fourier transform and perform second order (n=2) ButterWorth low pass filtering in the frequency domain. You should reuse your implementation of the fast Fourier transform from the previous task of this project (P1_1).

In the filterImage() method add your code for the second order (n=2) ButterWorth low pass filtering.

Implement the inverse fast Fourier transform in the method inverseFourierTransfrom().

You may use methods declared in the class Complex.java for your convenience.

The solution file is provided for qualitative comparison. It was generated with d0=10, i.e., with the command
 
java P1_2 10

Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed. 

**/

import java.time.Instant;
import java.time.Duration;
public class P1_2 {
	public P1_2(double d0) {
		Img img = new Img("ic512.png");
		Instant start = Instant.now();
		filterImage(img, d0);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void filterImage(Img i, double d0) {
		Complex[] F = fastFourierTransfrom(i);
		//Your code here
		int filter_rate = 2;
		for (int x = 0; x < i.img.length; x++){
			double frequency = (double) x / F.length;
			double factor = 1 / (1 + Math.pow(frequency / d0, 2 * filter_rate));
			F[x].mul(factor);
		}
		inverseFastFourierTransfrom(F, i);
    }

    public Complex[] fastFourierTransfrom(Img i) {
    	//Change this code
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
		new P1_2(Double.parseDouble(args[0]));
	}
}
