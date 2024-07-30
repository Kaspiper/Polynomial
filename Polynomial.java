import RationalNumber.RationalNumber;

/**
 * Models an algebraic polynomial expression
 *
 * @author Kasper
 *
 */
/**
 * Polynomial component with secondary methods.
 *
 *
 */
public abstract class Polynomial implements PolynomialKernel {

    public static final Polynomial ZERO = new PolynomialLinked();

    /**
     * Negates p. Convenience method.
     *
     * @param p
     *            the polynomial to be negated
     *
     * @ensures negate = -p
     *
     */
    private static void negate(Polynomial p) {
        int power = p.degree();
        if (power == 0) {
            RationalNumber constant = p.removeTerm(0);
            constant.negate();
            p.addTerm(constant, 0);
        } else {
            RationalNumber constant = p.removeTerm(power);
            negate(p);
            constant.negate();
            p.addTerm(constant, power);
        }
    }

    /**
     * Adds p to this.
     *
     * @param p
     *            the polynomial to be added
     * @updates this
     *
     * @clears p
     *
     * @ensures this = #this + p
     */
    public void add(Polynomial p) {
        int power;
        while (p.degree() > 0) {
            power = p.degree();
            RationalNumber constant = p.removeTerm(power);
            this.addTerm(constant, power);
        }
        this.addTerm(p.removeTerm(0), 0);
        p.clear();
    }

    // /**
    //  * Adds {@code Polynomial} p to this and returns the result. 
    //  *
    //  * @param p
    //  *            the polynomial to be added
    //  *
    //  * @ensures this = #this + p
    //  */
    // public Polynomial addkeep(Polynomial p) {
    //     int power;
    //     Polynomial answer = this.dupe();
    //     Polynomial copy = p.dupe();
    //     while (copy.degree() > 0) {
    //         power = copy.degree();
    //         RationalNumber constant = copy.removeTerm(power);
    //         answer.addTerm(constant, power);
    //     }
    //     answer.addTerm(copy.removeTerm(0), 0);
    //     copy.clear();
    //     return answer;
    // }

    /**
     * Subtracts p from this.
     *
     * @param p
     *            the polynomial to be subtracted
     * @updates this
     *
     * @clears p
     *
     * @ensures this = #this - p
     */
    public void subtract(Polynomial p) {
        negate(p);
        this.add(p);
    }

    // /**
    //  * Subtracts p from this.
    //  *
    //  * @param p
    //  *            the polynomial to be subtracted
    //  * @updates this
    //  *
    //  * @clears p
    //  *
    //  * @ensures this = #this - p
    //  */
    // public Polynomial subtractkeep(Polynomial p) {
    //     Polynomial answer = this.dupe();
    //     Polynomial copy = p.dupe();
    //     negate(copy);
    //     answer.add(copy);
    //     return answer;
    // }

    /**
     * Multiplies this by p.
     *
     * @param p
     *            the polynomial to be multiplied
     * @updates this
     *
     * @restores p
     *
     * @ensures this = #this * p
     */
    public void multiply(Polynomial p) {
        Polynomial answer = this.newInstance();
        int thisdegree = this.degree();
        while(thisdegree != 0){
            answer.add(p.multiplyTerm(this.removeTerm(thisdegree), thisdegree));
            
            thisdegree = this.degree();
        }
        answer.add(p.multiplyTerm(this.removeTerm(0), 0));
        this.transferFrom(answer);
    }

    /**
     * Multiplies this by {@code constant} X ^{@code degree}.
     *
     * @param p
     *            the polynomial to be multiplied
     * @updates this
     *
     * @ensures this = #this * p
     */
    public Polynomial multiplyTerm(RationalNumber constant, int degree) {
        Polynomial result = this.newInstance();
        Polynomial thiscopy = this.dupe();
        RationalNumber newconstant;
        int thisdegree = thiscopy.degree();
        while(thisdegree != 0){
            newconstant = thiscopy.removeTerm(thisdegree);
            newconstant.multiply(constant);

            result.addTerm(newconstant, thisdegree + degree);

            thisdegree = thiscopy.degree();
        }
        newconstant = thiscopy.removeTerm(0);
        newconstant.multiply(constant);
        result.addTerm(newconstant, 0 + degree);
        return result;
    }

    /**
     * Multiplies this by p.
     *
     * @param p
     *            the polynomial to be multiplied
     * @updates this
     *
     * @clears p
     *
     * @ensures this = #this * p
     */
    public Polynomial power(int exponent) {
        Polynomial answer = new PolynomialLinked(1);
        for(int i = 0; i < exponent; i++){
            answer.multiply(this);
        }
        return answer;

    }


    /**
    * Divides this by p.
    *
    * @param p
    *          the denominator polynomial
    *
    * @updates this
    *
    * @return the remainder of #this / p
    *
    * @ensures this = #this / p and divide = [remainder of #this/p]
    */
    public Polynomial divide(Polynomial p) {
        Polynomial quotient = this.newInstance();
        Polynomial thiscopy = this.dupe();
        Polynomial divisor = p.dupe();
        RationalNumber pconstant = p.getTerm(p.degree());
        int thiscopydegree = thiscopy.degree();
        int divisordegree = p.degree();

        while(thiscopydegree >= divisordegree){
            RationalNumber thisconstant = thiscopy.getTerm(thiscopydegree);
            thisconstant.divide(pconstant);
            quotient.addTerm(thisconstant, thiscopydegree - divisordegree);
            thiscopy.subtract(divisor.multiplyTerm(thisconstant, thiscopydegree - divisordegree));

            divisor = p.dupe();
            thiscopydegree = thiscopy.degree();
        }
        this.transferFrom(quotient);
        return thiscopy;


    }

    /**
    * Checks if p is a factor of this.
    *
    * @param p
    *            the polynomial to be checked
    * @return true iff this % p is 0
    *
    * @ensures isDivisbleBy = (this % p == 0)
    */
    public boolean isDivisbleBy(Polynomial p) {
        boolean answer = false;
        Polynomial thiscopy = this.dupe();
        Polynomial pcopy = p.dupe();

        Polynomial rem = thiscopy.divide(pcopy);
        if (rem.equals(PolynomialLinked.ZERO)) {
            answer = true;
        }

        return answer;
    }

    /**
    * Evaluates this at x = {@code x}.
    *
    * @param x
    *            the value used to evaluate the expression
    *
    * @return the evaluation of this at x
    *
    * @ensures evaluateAt = f({@code x}) where f(x) = this
    */
    public RationalNumber evaluateAt(RationalNumber x) {
       Polynomial thiscopy = this.dupe();
       RationalNumber answer = new RationalNumber();
    
       int thisdegree = thiscopy.degree();
       RationalNumber constant;

       while (thisdegree != 0) {
            constant = thiscopy.removeTerm(thisdegree);
            constant.multiply(x.power(thisdegree));
            answer.add(constant);
            thisdegree = thiscopy.degree();
        }
        answer.add(thiscopy.removeTerm(0));
       
        return answer;
   }

    /**
    * takes the derivative of this.
    *
    * @updates this
    *
    * @ensures this = d/dx(#this)
    *
    */
    public void takeDerivative() {
       Polynomial temp = this.newInstance();
       int thisdegree = this.degree();
       RationalNumber thisconstant;
       while (thisdegree != 0) {
           thisconstant = this.removeTerm(thisdegree);
           thisconstant.multiply(new RationalNumber(thisdegree));
           temp.addTerm(thisconstant, thisdegree - 1);
           thisdegree = this.degree();
       }
       this.transferFrom(temp);
   }

    /**
    * Evaluates and returns the derivative of this at {@code x}
    *
    *
    * @returns the value of the derivative of this at {@code x}
    *
    */
    public RationalNumber derivativeAt(RationalNumber x) {
        Polynomial thiscopy = this.dupe();
        thiscopy.takeDerivative();
        RationalNumber answer = thiscopy.evaluateAt(x);
        return answer;
    }

    /**
    * Evaluates the inddefinite integral of this
    *
    * [WARNING]: Constant of integration "C" is not included in this operation
    *
    * @ensures this = the indefinite integral of #this 
    *
    */
    public void takeIntegral() {
        Polynomial temp = this.newInstance();

        int thisdegree = this.degree();
        RationalNumber thisconstant;
        
        while (thisdegree != 0) {
            thisconstant = this.removeTerm(thisdegree);
            thisconstant.divide(new RationalNumber(thisdegree + 1));
            temp.addTerm(thisconstant, thisdegree + 1);
            thisdegree = this.degree();
        }
        thisconstant = this.removeTerm(0);
        temp.addTerm(thisconstant, 1);

        this.transferFrom(temp);
   }

    /**
    * Evaluates the definite integral of this
    *
    * [WARNING]: Constant of integration "C" is not included in this operation
    *
    * @returns the value of the indefinite integral of this from bounds {@code upperbound} to {@code lowerbound}
    *
    */
    public RationalNumber definiteIntegral(RationalNumber upperbound, RationalNumber lowerbound) {
        Polynomial thiscopy = this.dupe();
        thiscopy.takeIntegral();
        RationalNumber answer = thiscopy.evaluateAt(upperbound);
        answer.subtract(thiscopy.evaluateAt(lowerbound));
        return answer;
    }

    /**
    * Evaluates and returns the string representation of this
    *
    * @return the string representation of this
    */
    @Override
    public String toString() {
        Polynomial thisduplicate = this.dupe();
        StringBuilder answer = new StringBuilder();
        int thisdegree;
        RationalNumber thisconstant;
        while (this.degree() > 0) {
            thisdegree = this.degree();
            thisconstant = this.removeTerm(thisdegree);
            if(!thisconstant.equals(RationalNumber.ONE)){
                answer.append("(" + thisconstant + ")");
            }
            answer.append("x");
            if(thisdegree != 1){
                answer.append("^" + thisdegree );
            }
            answer.append( " + ");
        }
        thisconstant = this.removeTerm(0);
        answer.append("(" + thisconstant + ")");
        
        this.transferFrom(thisduplicate);
        return answer.toString();
    }


    /**
     * Returns True iff this and p are identical in content.
     *
     * @param p
     *            the Polynomial to be compared to this
     * @return p = this
     */
    public boolean equals(Polynomial p) {

        return this.toString().equals(p.toString());
    }
}
