/**
 * Models an algebraic polynomial expression
 *
 * @author Kasper
 *
 */
/**
 * Polynomial kernel component with secondary methods.
 *
 *
 */
public abstract class PolynomialSecondary implements Polynomial {
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
            int constant = p.removeTerm(0);
            p.addTerm(-constant, 0);
        } else {
            int constant = p.removeTerm(power);
            negate(p);
            p.addTerm(-constant, power);
        }
    }

    /**
     * returns a duplicate of p. Convenience method.
     *
     * @param p
     *            the polynomial to be duplicated
     *
     * @ensures duplicate = p
     *
     * @return a perfect copy of p
     */
    private static Polynomial duplicate(Polynomial p) {
        Polynomial copy = p.newInstance();
        Polynomial temp = p.newInstance();
        int degree;
        int constant;
        while (p.degree() > 0) {
            degree = p.degree();

            constant = p.removeTerm(degree);
            copy.addTerm(constant, degree);
            temp.addTerm(constant, degree);
        }
        constant = p.removeTerm(0);
        copy.addTerm(constant, 0);
        temp.addTerm(constant, 0);
        p.transferFrom(temp);
        return copy;
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
    void add(Polynomial p) {
        while (p.degree() > 0) {
            int power = p.degree();
            int constant = p.removeTerm(power);
            this.addTerm(constant, power);
        }
        this.addTerm(p.removeTerm(0), 0);
        p.clear();
    }

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
    void subtract(Polynomial p) {
        negate(p);
        this.add(p);
    }

//////////////////////////////////////////////////
//    DIFFERENT IMPLEMENTATIONS OF MULTIPLY    ///
//////////////////////////////////////////////////

//    void multiply(Polynomial p) {
//        Polynomial answer = this.newInstance();
//        Polynomial temp = this.newInstance();
//        while (p.degree() != 0) {
//            int ppower = p.degree();
//            int pconstant = p.removeTerm(ppower);
//
//            while (this.degree() != 0) {
//                int thispower = this.degree();
//                int thisconstant = this.removeTerm(thispower);
//                temp.addTerm(thisconstant, thispower);
//
//                answer.addTerm(pconstant * thisconstant, ppower + thispower);
//            }
//            int constantterm = this.removeTerm(0);
//            temp.addTerm(constantterm, 0);
//            answer.addTerm(pconstant * constantterm, ppower);
//
//            this.transferFrom(temp);
//        }
//        int pconstantterm = p.removeTerm(0);
//        while (this.degree() != 0) {
//            int thispower = this.degree();
//            int thisconstant = this.removeTerm(thispower);
//
//            answer.addTerm(pconstantterm * thisconstant, thispower);
//        }
//        answer.addTerm(pconstantterm * this.removeTerm(0), 0);
//        this.transferFrom(answer);
//    }
////////////////////////////////////////////////////////////////////////////////
//    void multiply(Polynomial p) {
//        int thispower = this.degree();
//        int thisconstant = this.removeTerm(thispower);
//        int ppower;
//        int pconstant;
//        Polynomial temp = this.newInstance();
//        if (thispower == 0) {
//            while (p.degree() > 0) {
//                ppower = p.degree();
//                pconstant = p.removeTerm(ppower);
//                this.addTerm(pconstant * thisconstant, ppower);
//                temp.addTerm(pconstant, ppower);
//            }
//            pconstant = p.removeTerm(0);
//            this.addTerm(pconstant * thisconstant, 0);
//            temp.addTerm(pconstant, 0);
//            p.transferFrom(temp);
//        } else {
//            while (p.degree() > 0) {
//                ppower = p.degree();
//                pconstant = p.removeTerm(ppower);
//                this.addTerm(pconstant * thisconstant, ppower);
//                temp.addTerm(pconstant, ppower);
//            }
//            pconstant = p.removeTerm(0);
//            this.addTerm(pconstant * thisconstant, 0);
//            temp.addTerm(pconstant, 0);
//            p.transferFrom(temp);
//            this.multiply(p);
//
//        }
//        this.add(temp);
//
//    }
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
    public void multiply(Polynomial p) {
        Polynomial pcopy = duplicate(p);
        Polynomial temp = this.newInstance();
        int thispower = this.degree();
        int thisconstant = this.removeTerm(thispower);

        int ppower;
        int pconstant;
        while (p.degree() > 0) {
            ppower = p.degree();
            pconstant = p.removeTerm(ppower);
            temp.addTerm(pconstant * thisconstant, ppower + thispower);
        }
        pconstant = p.removeTerm(0);
        temp.addTerm(pconstant * thisconstant, 0 + thispower);

        if (thispower != 0) {
            this.multiply(pcopy);
        }
        this.add(temp);
    }

//    /**
//     * Divides this by p.
//     *
//     * @param p
//     *            the denominator polynomial
//     *
//     * @updates this
//     *
//     * @return the remainder of #this / p
//     *
//     * @ensures this = #this / p and divide = [remainder of #this/p]
//     */
//    Polynomial divide(Polynomial p) {
//        Polynomial rem = this.newInstance();
//        Polynomial pcopy = duplicate(p);
//
//        int thisdegree = this.degree();
//        int thiscoeff = this.removeTerm(thisdegree);
//        this.addTerm(thiscoeff, thisdegree);
//
//        int pdegree = p.degree();
//        int pcoeff = p.removeTerm(pdegree);
//        p.addTerm(pcoeff, pdegree);
//
//        int factordegree;
//        int factorcoeff;
//        if (thisdegree >= pdegree) {
//            factordegree = thisdegree - pdegree;
//            factorcoeff = thiscoeff / pcoeff;
//
//            Polynomial temp = p.newInstance();
//            int ppower;
//            int pconstant;
//            while (p.degree() > 0) {
//                ppower = p.degree();
//                pconstant = p.removeTerm(ppower);
//                temp.addTerm(pconstant * factorcoeff, ppower + factordegree);
//            }
//            pconstant = p.removeTerm(0);
//            temp.addTerm(pconstant * factorcoeff, factordegree);
//
//            negate(temp);
//
//            this.add(temp);
//
//            rem = this.divide(pcopy);
//
//            this.addTerm(factorcoeff, factordegree);
//        } else {
//            rem = duplicate(this);
//        }
//        return rem;
//    }
//    /**
//     * Divides this by p.
//     *
//     * @param p
//     *            the denominator polynomial
//     *
//     * @updates this
//     *
//     * @return the remainder of #this / p
//     *
//     * @ensures this = #this / p and divide = [remainder of #this/p]
//     */
//    //p is the divisor
//    //#this is the dividend of division
//    //this is quotient of division
//    //return is remainder of division
//    //duplicate p, multiply p by
//    public Polynomial divide(Polynomial p) {
//        Polynomial quotient = this.newInstance();
//        Polynomial temp = this.newInstance();
//
//        int divpower = p.degree();
//        int divcoeff = p.removeTerm(divpower);
//
//        int thispower = this.degree();
//        int thiscoeff = this.removeTerm(thispower);
//
//        int quotcoeff = thiscoeff / divcoeff;
//        int quotpower = thispower - divpower;
//
//        while (this.degree() > p.degree()) {
//            
//        }
//
//    }

//    /**
//     * Evaluates this at x = {@code x}.
//     *
//     * @param x
//     *            the value used to evaluate the expression
//     *
//     * @return the evaluation of this at x
//     *
//     * @ensures evaluateAt = f({@code x}) where f(x) = this
//     */
//    double evaluateAt(double x) {
//        Polynomial p = duplicate(this);
//        double answer = 0;
//
//        int ppower;
//        int pconstant;
//        while (p.degree() > 0) {
//            ppower = p.degree();
//            pconstant = p.removeTerm(ppower);
//            answer = answer + (Math.pow(x, ppower) * pconstant);
//        }
//        pconstant = p.removeTerm(0);
//        answer = answer + pconstant;
//        return answer;
//    }
//
//    /**
//     * Checks if p is a factor of this.
//     *
//     * @param p
//     *            the polynomial to be checked
//     * @return true iff this % p is 0
//     *
//     * @ensures isMultiple = (this % p == 0)
//     */
//    boolean isMultiple(Polynomial p) {
//        boolean answer = false;
//        Polynomial thistemp = duplicate(this);
//        Polynomial pcopy = duplicate(p);
//
//        Polynomial rem = this.divide(pcopy);
//        this.transferFrom(thistemp);
//
//        if (rem.removeTerm(0) == 0 && rem.degree() == 0) {
//            answer = true;
//        }
//
//        return answer;
//    }
//
//    /**
//     * takes the derivative this.
//     *
//     * @updates this
//     *
//     * @ensures this = d/dx(#this)
//     *
//     */
//    void takeDerivative() {
//        Polynomial temp = this.newInstance();
//        int thispower;
//        int thisconstant;
//        while (this.degree() > 0) {
//            thispower = this.degree();
//            thisconstant = this.removeTerm(thispower);
//            temp.addTerm(thisconstant * thispower, thispower - 1);
//        }
//        this.clear();
//        this.transferFrom(temp);
//    }
//
//    /**
//     * Evaluates the definite integral of this with bounds {@code upperbound} to
//     * {@code lowerbound}.
//     *
//     * @return the area bounded by the polynomial curve and bounds of
//     *         integration
//     *
//     * @param upperbound
//     *            the upper bound of integration
//     *
//     * @param lowerbound
//     *            the lower bound of integration
//     *
//     * @ensures defIntegral = F({@code upperbound}) - F({@code lowerbound})
//     *          where d/dx(F(x)) = this
//     *
//     *
//     */
//    double defIntegral(double upperbound, double lowerbound) {
//
//        double answer = 0;
//        int thisdegree;
//        int thisconstant;
//
//        thisdegree = this.degree();
//        thisconstant = this.removeTerm(thisdegree);
//        if (thisdegree == 0) {
//            answer = (thisconstant * upperbound - thisconstant * lowerbound);
//        } else {
//            answer = thisconstant * Math.pow(upperbound, thisdegree)
//                    - thisconstant * Math.pow(lowerbound, thisdegree);
//            answer = answer + this.defIntegral(upperbound, lowerbound);
//        }
//        this.addTerm(thisconstant, thisdegree);
//        return answer;
//    }

    @Override
    public String toString() {

        Polynomial thisduplicate = duplicate(this);
        StringBuilder answer = new StringBuilder();

        int thisdegree;
        int thisconstant;
        while (this.degree() > 0) {

            thisdegree = this.degree();
            thisconstant = this.removeTerm(thisdegree);

            answer.append(thisconstant + "x^(" + thisdegree + ") + ");
        }

        thisconstant = this.removeTerm(0);

        answer.append(thisconstant);

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
