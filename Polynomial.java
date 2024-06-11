/**
 * 
 *
 * @author Kasper
 *
 */
/**
 * Polynomial kernel component with primary methods.
 *
 *
 */
public interface Polynomial extends PolynomialKernel {
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
     * @requires -MAX_INT <= constant + current <= MAX_INT where current = the
     *           corresponding coefficient to {@code power} in this and -MAX_INT
     *           <= power <= MAX INT
     *
     * @ensures this = constant * (x ^ power) + #this
     */
    @Override
    void addTerm(int constant, int power);

    /**
     * Removes the term raised to {@code power} and reports its coefficient.
     *
     * @param power
     *            the degree of the term to be removed
     * @return the coefficient
     * @updates this
     * @ensures #this = removeTerm * (x ^ power) + this
     */
    @Override
    int removeTerm(int power);

    /**
     * Reports the degree of this.
     *
     * @return the highest power in {@code this}.
     * @ensures degree = [the exponent of the highest order term in this]
     *
     */
    @Override
    int degree();

}
