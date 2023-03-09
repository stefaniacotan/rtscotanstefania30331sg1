package laboratory1;

public class ComplexNumbers {
    int real;
    int imaginary;
    public ComplexNumbers (int real, int imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    ComplexNumbers sum(ComplexNumbers b) {
        ComplexNumbers s = new ComplexNumbers(real, imaginary);
        s.real = this.real + b.real;
        s.imaginary = this.imaginary + b.imaginary;

        return s;
    }

    ComplexNumbers product(ComplexNumbers b) {
        ComplexNumbers p = new ComplexNumbers(real, imaginary);
        p.real = this.real * b.real - this.imaginary * b.imaginary;
        p.imaginary = this.real * b.imaginary + b.real * this.imaginary;
        return p;
    }

    @Override
    public String toString() {
        return this.real + "+" + this.imaginary + "i";
    }

    public static void main(String[] args) {
        ComplexNumbers number1 = new ComplexNumbers(2, 5);
        ComplexNumbers number2 = new ComplexNumbers(4, -1);
        ComplexNumbers suma = number1.sum(number2);
        ComplexNumbers produs = number1.product(number2);
        System.out.println("The sum is: " + suma);
        System.out.println("The product is: " + produs);
    }
}
