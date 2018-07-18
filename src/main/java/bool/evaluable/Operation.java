package bool.evaluable;

import java.util.Objects;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Operation implements Evaluable, Comparable<Operation> {
    private final Operator operator;
    private Evaluable left;
    private Evaluable right;

    public Operation(Operator operator, Evaluable left, Evaluable right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public boolean overlapsWith(Operation that) {
        if (this.left == that.right) {
            this.left = that;
            return true;
        }
        if (this.right == that.left) {
            this.right = that;
            return true;
        }
        return false;
    }

    private void applyLaw() {
    }

    @Override
    public boolean evaluate() throws IllegalArgumentException {
        switch (operator.getType()) {
            case AND:
                return left.evaluate() && right.evaluate();
            case NAND:
                return !(left.evaluate() && right.evaluate());
            case XOR:
                return (left.evaluate() && !right.evaluate()) || (!left.evaluate() && right.evaluate());
            case XNOR:
                return !((left.evaluate() && !right.evaluate()) || (!left.evaluate() && right.evaluate()));
            case OR:
                return left.evaluate() || right.evaluate();
            case NOR:
                return !(left.evaluate() || right.evaluate());
            default:
                throw new IllegalArgumentException(operator.toString());
        }
    }

    @Override
    public int compareTo(Operation that) {
        return this.operator.getType().getPrecedence() - that.operator.getType().getPrecedence();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(operator, operation.operator) &&
                Objects.equals(left, operation.left) &&
                Objects.equals(right, operation.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, left, right);
    }

    @Override
    public String toString() {
        return "(" + left +
                " " + operator +
                " " + right +
                ")";
    }
}
