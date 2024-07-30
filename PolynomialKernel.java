
import RationalNumber.RationalNumber;
/**
 * Models an algebraic polynomial expression
 *
 * @author Kasper
 *
 * Polynomial kernel component with primary methods.
 *
 *
 */
public interface PolynomialKernel{
        /**
     * Adds a term with coefficient {@code constant} and degree {@code power} to
     * this.
     *
     * @param constant
     *            the coefficient of the term to be added
     * @param power
     *            the degree of the term to be added
     *
     * @updates this
     *
     * @ensures this = constant * (x ^ power) + #this
     */
    void addTerm(RationalNumber constant, int power);

    /**
     * Removes the term raised to {@code power} and reports its coefficient.
     *
     * @param power
     *            the degree of the term to be removed
     * @return the coefficient
     * 
     * @updates this
     * 
     * @ensures #this = removeTerm * (x ^ power) + this
     */
    RationalNumber removeTerm(int power);

    /**
     * Retrieves the term raised to {@code power} and reports its coefficient.
     *
     * @param power
     *            the degree of the term to be removed
     * @return the coefficient
     */
    public RationalNumber getTerm(int power);

    /**
     * Reports the degree of this.
     *
     * @return the highest power in {@code this}.
     * @ensures degree = [the exponent of the highest order term in this]
     *
     */
    int degree();

    /**
     * Resets this to it's default value upon initializiation.
     *
     * 
     * @ensures this = 0
     *
     */
    void clear();


    /**
     * returns a duplicate of p. Convenience method.
     *
     * @param p
     *            the polynomial to be duplicated
     *
     * @ensures dupe = p
     *
     * @return a perfect copy of p
     */
    public Polynomial dupe();

    /**
     * Creates and returns a new polynomial instance of this.
     *
     * @return a polynomial of the same type as this initialized to default value
     */
    Polynomial newInstance();

    /**
     * Replaces the polynomial represented by this with the polynomial in {@code source}
     *
     * @ensures this = #source and source = 0
     */
    void transferFrom(Polynomial source);
}