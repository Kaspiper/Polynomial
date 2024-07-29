

public class PolynomialTest {
    
    public static void main(String[] args) {
        Polynomial a = new PolynomialLinked(6,0,-9,0,18);
        Polynomial b = new PolynomialLinked(1,-3);
       

    
            

        System.out.println("A: " + a.toString());
        System.out.println("B: " + b.toString());
        System.out.println("REMAINDER: " + a.divide(b));
        System.out.println("A / B: " + a.toString());
        

   

    }
}
