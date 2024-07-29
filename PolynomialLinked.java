import RationalNumber.RationalNumber;


/**
 * 
 * Models an algebraic polynomial expression
 *
 * 
 * @author Kasper
 *
 *
 * Polynomial kernel component with secondary methods.
 *
 *
 */
public class PolynomialLinked extends Polynomial{


    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Node class for singly linked list nodes.
     */
    private final class Term {

        /**
         * Exponent.
         */
        private int power;
        /**
         * Constant.
         */
        private RationalNumber constant;

        /**
         * Next node in singly linked list, or null.
         */
        private Term next;

    }

    /**
     * Top node of singly linked list.
     */
    private Term expression;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.expression = new Term();
        this.expression.power = 0;
        this.expression.constant = new RationalNumber();
        this.expression.next = null;

    }


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
    @Override
    public void addTerm(RationalNumber constant, int power) {
        assert this.expression != null : "NULL TERM FOUND";
        Term termptr = this.expression;
        if (!constant.equals(RationalNumber.ZERO)) {
            while(termptr.next != null && power < termptr.next.power){
                termptr = termptr.next;                                         //node traversal
            }
            if(power == this.expression.power){                                 //head case
                termptr.constant.add(constant);
                if(termptr.constant.equals(RationalNumber.ZERO) && power != 0){
                    this.expression = termptr.next;
                }
            }else if(termptr.next != null && power == termptr.next.power){      //add case
                termptr.next.constant.add(constant);
                if(termptr.next.constant.equals(RationalNumber.ZERO) && power != 0){
                    termptr.next = termptr.next.next;
                }
            }else{
                Term newTerm = new  Term();                                     //insertion
                newTerm.constant = constant;
                newTerm.power = power;
                if(power > this.expression.power){
                    newTerm.next = this.expression;
                    this.expression = newTerm;
                }else{
                    newTerm.next = termptr.next;
                    termptr.next = newTerm;
                }
            }
        }
    }



    

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
    public RationalNumber removeTerm(int power) {
        assert this.expression != null : "NULL TERM FOUND";
        RationalNumber answer = new RationalNumber(0);
        Term termptr = this.expression;
        if (power == this.expression.power) {           //remove head case
            if (power != 0) {
                answer = termptr.constant;
                this.expression = this.expression.next;
            } else {
                answer = termptr.constant;
                termptr.constant = new RationalNumber(0);
            }
        } else {
            while (termptr.next != null && power != termptr.next.power) {
                termptr = termptr.next;                 //ptr traversal
            }
            if (termptr.power != 0) {
                answer = termptr.next.constant;         //node deletion
                termptr.next = termptr.next.next;
            }
        }
        return answer;
    }


    /**
     * Retrieves the term raised to {@code power} and reports its coefficient.
     *
     * @param power
     *            the degree of the term to be found
     * 
     * 
     * @return the coefficient
     */
    @Override
    public RationalNumber getTerm(int power) {
        assert this.expression != null : "NULL TERM FOUND";
        RationalNumber answer = new RationalNumber(0);
        Term termptr = this.expression;
        while(termptr.power != power && termptr.next != null){
            termptr = termptr.next;
        }
        if(power == termptr.power){
            answer = new RationalNumber(termptr.constant);
        }
        return answer;
    }
   
    /**
     * Reports the degree of this.
     *
     * @return the highest power in {@code this}.
     * @ensures degree = [the exponent of the highest order term in this]
     *
     */
    @Override
    public final int degree() {
        assert this.expression != null : "NULL TERM FOUND";

        return this.expression.power;

    }

    @Override
    public final void clear() {
        this.createNewRep();
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
    @Override
    public Polynomial dupe() {
        Polynomial copy = this.newInstance();
        Term termptr = this.expression;
        while(termptr.next!=null){
            copy.addTerm(new RationalNumber(termptr.constant), termptr.power);
            termptr = termptr.next;         //ptr traversal
        }
        copy.addTerm(new RationalNumber(termptr.constant), termptr.power);
        return copy;
    }

    @Override
    public final Polynomial newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void transferFrom(Polynomial source) {
        assert this.expression != null : "NULL TERM FOUND";
        int deg;
        this.createNewRep();
        while (source.degree() != 0) {
            deg = source.degree();
            this.addTerm(source.removeTerm(deg), deg);
        }
        this.addTerm(source.removeTerm(0), 0);
    }

    /**
     * No-argument constructor.
     */
    public PolynomialLinked() {
        this.createNewRep();

    }

    /**
     * 
     */
    public PolynomialLinked(RationalNumber... coeff) {
        this.createNewRep();
        int degree = coeff.length - 1;
        for (RationalNumber constant : coeff) {
            this.addTerm(constant, --degree);
        }
    }

    /**
     * 
     */
    public PolynomialLinked(int... coeff) {
        this.createNewRep();
        int degree = coeff.length - 1;
        for (int constant : coeff) {
            this.addTerm(new RationalNumber(constant), degree--);
        }
    }



    
}
