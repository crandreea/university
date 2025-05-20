package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumarComplex {
    private final double re; //partea reala din nr complex
    private final double im; //partea imaginara din nr complex

    public NumarComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public NumarComplex adunare(NumarComplex o) {
        return new NumarComplex(this.re + o.re, this.im + o.im);
    }

    public NumarComplex scadere(NumarComplex o) {
        return new NumarComplex(this.re - o.re, this.im - o.im);
    }

    public NumarComplex inmultire(NumarComplex o) {
        return new NumarComplex(this.re * o.re - this.im * o.im, this.re * o.im + o.re * this.im);
    }

    public NumarComplex impartire(NumarComplex o) {
        double numitor = o.re * o.re + o.im * o.im;
        double real = (this.re * o.re + this.im * o.im) / numitor;
        double imag = (this.im * o.re - this.re * o.im) / numitor;
        return new NumarComplex(real, imag);
    }

    @Override
    public String toString() { //returneaza un nr complex cu im != 0
        return BigDecimal.valueOf(re).setScale(3, RoundingMode.DOWN) + (im != 0 ? (im > 0 ? " + " : " - ") + BigDecimal.valueOf(Math.abs(im)).setScale(3, RoundingMode.UP) + " * i" : "");
    }
}
