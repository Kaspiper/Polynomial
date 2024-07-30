public class PolynomialTest {
    
    public static void main(String[] args) {
       
        Polynomial a = new PolynomialLinked(3,-2);
        int exponent = 4;
        System.out.println(a + " to the power of " + exponent + " is " + a.power(exponent));

    }
}
