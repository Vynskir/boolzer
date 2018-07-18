package bool.evaluable;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Operator {
    private final Type type;

    public Operator(char name) {
        this(Character.toString(name));
    }

    public Operator(String name) {
        switch (name) {
            case "*":
                this.type = Type.AND;
                break;
            case "⊼":
                this.type = Type.NAND;
                break;
            case "⊕":
                this.type = Type.XOR;
                break;
            case "⊙":
                this.type = Type.XNOR;
                break;
            case "+":
                this.type = Type.OR;
                break;
            case "⊽":
                this.type = Type.NOR;
                break;
            default:
                throw new IllegalArgumentException(name);
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.name();
    }

    public enum Type {
        AND(1),
        NAND(2),
        XOR(3),
        XNOR(4),
        OR(5),
        NOR(6);

        private int precedence;

        Type(int precedence) {
            this.precedence = precedence;
        }

        public int getPrecedence() {
            return precedence;
        }
    }
}
