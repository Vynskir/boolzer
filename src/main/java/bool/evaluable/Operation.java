package bool.evaluable;

public class Operation implements Evaluable, Comparable<Operation> {
    private Operator operator;

    private Evaluable left;
    private Evaluable right;

    public Operation(Operator operator, Evaluable left, Evaluable right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        if (operator.getType() == Operator.Type.AND) {
            return left.evaluate() && right.evaluate();
        } else if (operator.getType() == Operator.Type.NAND) {
            return !(left.evaluate() && right.evaluate());
        } else if (operator.getType() == Operator.Type.XOR) {
            return (left.evaluate() && !right.evaluate()) || (!left.evaluate() && right.evaluate());
        } else if (operator.getType() == Operator.Type.XNOR) {
            return !((left.evaluate() && !right.evaluate()) || (!left.evaluate() && right.evaluate()));
        } else if (operator.getType() == Operator.Type.OR) {
            return left.evaluate() || right.evaluate();
        } else if (operator.getType() == Operator.Type.NOR) {
            return !(left.evaluate() || right.evaluate());
        } else {
            throw new IllegalArgumentException(operator.toString());
        }
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

    @Override
    public int compareTo(Operation that) {
        return this.operator.getType().getPrecedence() - that.operator.getType().getPrecedence();
    }

    @Override
    public String toString() {
        return "(" + left +
                " " + operator +
                " " + right +
                ")";
    }
}
