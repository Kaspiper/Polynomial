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
public class PolynomialM extends Polynomial {
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
        private int constant;

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
        this.expression.constant = 0;
        this.expression.next = null;

    }

    /**
     * No-argument constructor.
     */
    public PolynomialM() {
        this.createNewRep();

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
     * @requires -MAX_INT <= constant + current <= MAX_INT where current = the
     *           corresponding coefficient to {@code power} in this and -MAX_INT
     *           <= power <= MAX INT
     *
     * @ensures this = constant * (x ^ power) + #this
     */
    @Override
    public final void addTerm(int constant, int power) {
        assert this.expression != null : "NULL TERM FOUND";
        if (constant != 0) {
            Term termptr = this.expression;
            if (power > this.expression.power) { //new head case
                Term newTerm = new Term();
                newTerm.constant = constant;
                newTerm.power = power;
                newTerm.next = this.expression;
                this.expression = newTerm;
            } else {
                while (termptr.next != null && power <= termptr.next.power) {
                    termptr = termptr.next; //ptr traversal
                }
                if (termptr.power == power) {
                    termptr.constant = termptr.constant + constant; //add cases
                } else {
                    Term newTerm = new Term();
                    newTerm.constant = constant;
                    newTerm.power = power;
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
    public final int removeTerm(int power) {
        assert this.expression != null : "NULL TERM FOUND";
        int answer = 0;
        Term termptr = this.expression;
        if (power == this.expression.power) { //remove head case
            if (power != 0) {
                answer = termptr.constant;
                this.expression = this.expression.next;
            } else {
                answer = termptr.constant;
                termptr.constant = 0;
            }
        } else {
            while (termptr.next != null && power != termptr.next.power) {
                termptr = termptr.next; //ptr traversal
            }
            if (termptr.power != 0) {
                answer = termptr.next.constant;
                termptr.next = termptr.next.next;
            }
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
}
