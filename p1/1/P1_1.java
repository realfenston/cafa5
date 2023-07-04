/**

In this task 1 of project 1 you will implement the fast Fourier transform and change the image to the Fourier spectrum in the method fourierSpectrum(). Your task is to implement the missing code in the method fastFourierTransform(). The implementation details of the FFT can be obtained in section 4.11 of our Textbook. 

Use the log transformation and ensure that all values are in the range 0 ... 255. 

You may use methods declared in the class Complex.java for your convenience and you may add new methods to that class if necessary. 

The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed.

For your reference, we are able generate the Fourier spectrum of the file rectangle1024.png in < 1 seconds. 

**/

import java.time.Instant;
import java.time.Duration;

public class P1_1 {
	public P1_1() {
		Img img = new Img("/Users/fengshihang/Documents/ComputerImage/p1/1/rectangle1024.png");
		Instant start = Instant.now();
		fourierSpectrum(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void fourierSpectrum(Img i) {
    	Complex[] F = fastFourierTransfrom(i);
		double max = Double.NEGATIVE_INFINITY;
		for (int x = 0; x < F.length; x++)
			max = Math.max(F[x].getNorm(), max);
		for (int x = 0; x < i.img.length; x++)
			i.img[x] = (byte)(255 / Math.log(256)*Math.log(255/max*F[x].getNorm()+1));
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
			y[k] = q1;
			y[k + n/2] = q2;
		}

		return y;
	}
		
	public static void main(String[] args) {
		new P1_1();
	}
}
