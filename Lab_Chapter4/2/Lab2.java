/**

In this task you will implement the inverse Fourier transform and perform filtering in the frequency domain. You should reuse your implementation of the Fourier transform from the previous Lab 1. 

In the filterImage() method add one line of code that changes the Fourier transformed image such that it is 0 at the center.

Implement the inverse Fourier transform in the method inverseFourierTransfrom().

You may use methods declared in the class Complex.java for your convenience.

The solution files are provided for qualitative comparison. Output could be different because of differences in floating point arithmetic and differences in the way the rescaling is performed. 

**/

import java.time.Instant;
import java.time.Duration;
public class Lab2 {
	public Lab2() {
		Img img = new Img("ic128.png");
		Instant start = Instant.now();
		filterImage(img);
		Instant stop = Instant.now();
		System.out.println("Elapsed time: "+Duration.between(start, stop).getSeconds()+"s");
		img.save();
	}

    public void filterImage(Img i) {
		Complex[] F = fourierTransfrom(i);
		//Your code here
		inverseFourierTransfrom(F, i);
    }

    public Complex[] fourierTransfrom(Img i) {
    	//Change this to your code from Lab 1
    	Complex[] F = new Complex[i.width*i.height];
		for (int x=0;x<i.img.length;x++)
			F[x] = new Complex();
		return F;
	}

	private void inverseFourierTransfrom(Complex[] F, Img i) {
		//Your code here
	} 

	public static void main(String[] args) {
		new Lab2();
	}
}
