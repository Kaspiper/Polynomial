import RationalNumber.RationalNumber;

public class PolynomialTest {
    
    public static void main(String[] args) {
       
        Polynomial a = new PolynomialLinked(1,9,2,1,4);
        Polynomial b = new PolynomialLinked(1,0,0,0,1);
        Polynomial c = a.divide(b);
        System.out.println(c);

      

        
    }
}
