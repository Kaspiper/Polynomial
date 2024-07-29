package RationalNumber;
import java.math.*;




/**
 * 
 * @author Kasper Lau (Kaspiper)
 * 
 * 
 * Represents numbers and indivisible terms as simplified fractions,
 * bypassing any arithmetic with floating point and MAX_INT limitations. 
 * Built using java.math BigInteger class. Use for exact math. 
 *
 */




public class RationalNumber implements Comparable<RationalNumber>{

    public static final RationalNumber ZERO = new RationalNumber(0);

    public static final RationalNumber ONE = new RationalNumber(1);


    private BigInteger NUMERATOR;
     
    private BigInteger DENOMINATOR;

    /**
     * One of the basic getter/setter methods for this.
     * Getter method for the numerator of the instance
     *
     * @return the numerator of this
     */
    public BigInteger getNumerator(){
        return this.NUMERATOR;
    }


    /**
     * One of the basic getter/setter methods for this.
     * Getter method for the denominator of the instance
     *
     * @return the denominator of this
     */
    public BigInteger getDenominator(){
        return this.DENOMINATOR;
    }

    /**
     * One of the basic getter/setter methods for this.
     * Setter method for the numerator of the instance
     *
     * @modifies this
     * 
     * @param a
     *          the BigInteger for the new numerator
     */
    public void setNumerator(BigInteger a){
        this.NUMERATOR = new BigInteger(a.toByteArray());
    }


     /**
     * One of the basic getter/setter methods for this.
     * Setter method for the denominator of the instance
     *
     * @modifies this
     * 
     * @param a
     *          the BigInteger for the new denominator
     */
    public void setDenominator(BigInteger a){
        this.DENOMINATOR = new BigInteger(a.toByteArray());
    }
   

    /**
     * Private method that calculates the least common multiple of two Big Integers.
     * Method utilizes the built in gcd() method from BigInteger
     * Used to simplify the fraction representation in RationalNumber
     *
     * @param a 
     *          one of the two BigInteger to calculate the lcm
     * @param b
     *          one of the two BigInteger to calculate the lcm
     * 
     * 
     */
    private BigInteger lcm(BigInteger a, BigInteger b){
        BigInteger answer = (a.multiply(b)).abs();
        return answer.divide(a.gcd(b));
    }


    public void negate(){
        this.NUMERATOR = this.NUMERATOR.negate();
    }


    /**
     *  Instance method used to add RationalNumber {@code b} to this
     * 
     * @modifies this
     * 
     * @param b
     *          the RationalNumber being added to this
     */
    public void add(RationalNumber b){
        BigInteger lcmtemp = lcm(this.DENOMINATOR, b.getDenominator());
        this.NUMERATOR = this.NUMERATOR.multiply(lcmtemp.divide(this.DENOMINATOR));
        this.NUMERATOR = this.NUMERATOR.add(b.getNumerator().multiply(lcmtemp.divide(b.getDenominator())));
        this.DENOMINATOR = lcmtemp;
    }


    /**
     *  Instance method used to add BigInteger {@code b} to this
     * 
     * @modifies this
     * 
     * @param b
     *          the BigInteger being added to this
     */
    public void add(BigInteger b){
        this.NUMERATOR = this.NUMERATOR.add(b.multiply(this.DENOMINATOR));
    }


    /**
     *  Instance method used to subtract RationalNumber {@code b} from this
     * 
     * @modifies this
     * 
     * @param b
     *          the RationalNumber being subtracted from this
     */
    public void subtract(RationalNumber b){
        BigInteger lcmtemp = lcm(this.DENOMINATOR, b.getDenominator());
        this.NUMERATOR = this.NUMERATOR.multiply(lcmtemp.divide(this.DENOMINATOR));
        this.NUMERATOR = this.NUMERATOR.subtract(b.getNumerator().multiply(lcmtemp.divide(b.getDenominator())));
        this.DENOMINATOR = lcmtemp;
    }



    /**
     *  Instance method used to subtract BigInteger {@code b} from this
     * 
     * @modifies this
     * 
     * @param b
     *          the BigInteger being subtracted from this
     */
    public void subtract(BigInteger b){
        this.NUMERATOR = this.NUMERATOR.subtract(b.multiply(this.DENOMINATOR));
    }



    /**
     *  Instance method used to multiply this with another RationalNumber {@code b}
     * 
     * @modifies this
     * 
     * @param b
     *          the RationalNumber being multiplied to this
     */
    public void multiply(RationalNumber b){
        BigInteger crossGCD = this.NUMERATOR.gcd(b.getDenominator()).multiply(this.DENOMINATOR.gcd(b.getNumerator()));
        this.NUMERATOR = this.NUMERATOR.multiply(b.getNumerator());
        this.NUMERATOR = this.NUMERATOR.divide(crossGCD);

        this.DENOMINATOR = this.DENOMINATOR.multiply(b.getDenominator());
        this.DENOMINATOR = this.DENOMINATOR.divide(crossGCD);
    }

    /**
     *  Instance method used to multiply this with a BigInteger {@code b}
     * 
     * @modifies this
     * 
     * @param b
     *          the BigInteger being multiplied to this
     */
    public void multiply(BigInteger b){
        BigInteger GCD = b.gcd(this.DENOMINATOR);
        this.DENOMINATOR = this.DENOMINATOR.divide(GCD);
        this.NUMERATOR = this.NUMERATOR.multiply(b.divide(GCD));
    }
    

    /**
     *  Instance method used to divide this by RationalNumber {@code b}
     * 
     * @modifies this
     * 
     * @param b
     *          the RationalNumber being dividing this
     */
    public void divide(RationalNumber dividend){
        BigInteger temp = new BigInteger(dividend.getDenominator().toByteArray());

        dividend.setDenominator(dividend.getNumerator());
        dividend.setNumerator(temp);
        
        this.multiply(dividend);

        dividend.setNumerator(dividend.getDenominator());
        dividend.setDenominator(temp);
    }

     /**
     * No argument constructor for the class RationalNumber with numeric value initialized to 0.
     *  
     * @returns a new instance of RationalNumber with numeric value 0.
     */
    public RationalNumber(){
        this.NUMERATOR = new BigInteger("0");
        this.DENOMINATOR = new BigInteger("1");
    }


     /**
     * Constructor for the class RationalNumber with numeric value initialized to {@code a} / {@code b}.
     *  
     * @returns a new instance of RationalNumber 
     */
    public RationalNumber (int a, int b){
        BigInteger bigA = new BigInteger(""+a);
        BigInteger bigB = new BigInteger(""+b);
        BigInteger gcdAB = bigA.gcd(bigB);
        this.NUMERATOR = bigA.divide(gcdAB);
        this.DENOMINATOR = bigB.divide(gcdAB);
        
    }

     /**
     * Constructor for the class RationalNumber with numeric value initialized to {@code a} / {@code b}.
     *  
     * @returns a new instance of RationalNumber 
     */
    public RationalNumber (BigInteger a, BigInteger b){
        BigInteger gcdAB = a.gcd(b);
        this.NUMERATOR = (new BigInteger(a.toByteArray())).divide(gcdAB);
        this.DENOMINATOR = (new BigInteger(b.toByteArray())).divide(gcdAB);
    }


     /**
     * Constructor for the class RationalNumber with numeric value initialized to {@code a}.
     *  
     * @returns a new instance of RationalNumber 
     */
    public RationalNumber (int a){
        this.NUMERATOR = new BigInteger(""+a);
        this.DENOMINATOR = new BigInteger("1");
    }

     /**
     * Constructor for the class RationalNumber with numeric value initialized to {@code a}.
     *  
     * @returns a new instance of RationalNumber 
     */
    public RationalNumber (BigInteger a){
        this.NUMERATOR = new BigInteger(a.toByteArray());
        this.DENOMINATOR = new BigInteger("1");
    }

     /**
     * Constructor for the class RationalNumber with numeric value initialized to {@code a}.
     *  
     * @returns a new instance of RationalNumber 
     */
    public RationalNumber (RationalNumber a){
        this.NUMERATOR = new BigInteger(a.getNumerator().toByteArray());
        this.DENOMINATOR = new BigInteger(a.getDenominator().toByteArray());
    }

     /**
     * Convenience method inherited from java.lang.Object used to create a duplicate RationalNumber
     *  
     * @returns a new instance of RationalNumber with the same numeric value as this
     */
    @Override
    public RationalNumber clone(){
        return new RationalNumber(this.NUMERATOR, this.DENOMINATOR);
    }


     /**
     * Method inherited from java.lang.Object used to check equivalency between instances of RationalNumber
     *  
     * @returns true if $this = {@code a}
     */
    public boolean equals(RationalNumber a){
        boolean equals = false;
        if(this.NUMERATOR.equals(a.getNumerator()) && this.DENOMINATOR.equals(a.getDenominator())){
            equals = true;
        }
        return equals;  
    }

    /**
     * Method inherited from java.lang Comparable used to compare instances of RationalNumber
     *  
     * @returns greater than 0 if $this > {@code a}, less than 0 if $this < {@code a}, and 0 if $this = {@code a}
     */
    @Override
    public int compareTo(final RationalNumber a){
        return this.NUMERATOR.multiply(a.getDenominator()).compareTo(this.DENOMINATOR.multiply(a.getNumerator()));

    }

    /**
     * Method inherited from java.lang.Object used to get the String representation of this
     *  
     * @returns the String representation of this
     */
    @Override
    public String toString(){
        String stringRep;
        if(this.NUMERATOR.equals(BigInteger.ZERO)){
            stringRep = "0";
        }else if(this.DENOMINATOR.equals(BigInteger.ONE)){
            stringRep = this.NUMERATOR.toString();
        }else{
            stringRep = this.NUMERATOR.toString() + " / " + this.DENOMINATOR.toString();
        }
        return stringRep;
    }


    //DEBUGGING MODE SHOWS NUMERATOR AND DENOMINATOR ALWAYS
    // @Override
    // public String toString(){
    //     String stringRep;
    
    //     stringRep = this.NUMERATOR.toString() + " / " + this.DENOMINATOR.toString();
        
    //     return stringRep;
    // }
    void main(){

    }
}
