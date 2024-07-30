import RationalNumber.RationalNumber;

public class PolynomialTest {
    
    public static void main(String[] args) {
       
        Polynomial a = new PolynomialLinked(3,-2,12,-8);
        Polynomial b = new PolynomialLinked(new RationalNumber(1), new RationalNumber(-2,3));
        
        System.out.print(a + " divided by " + b + " is ");
        Polynomial c = a.divide(b);
        System.out.println(a);
        System.out.println("REMAINDER: " + c);

    }
}
