package bool.expression;

import bool.evaluable.Group;
import bool.evaluable.Operator;
import bool.evaluable.Variable;

import java.util.Objects;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Expression {
    private final String expression;
    private final TruthTable truthTable;

    public Expression(String str) {
        expression = format(str);
        validate(expression);

        Group main = new Group();

        boolean not = false;
        int depth = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isAlphabetic(expression.charAt(i))) {
                if (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    String name = String.valueOf(expression.charAt(i)) + String.valueOf(expression.charAt(i + 1));
                    main.add(new Variable(name, not), depth);
                } else {
                    main.add(new Variable(expression.charAt(i), not), depth);
                }
                not = false;
            } else if (expression.charAt(i) == '(') {
                depth++;
                main.add(new Group(not), depth);
                not = false;
            } else if (expression.charAt(i) == ')') {
                depth--;
            } else if (expression.charAt(i) == '!') {
                not = !not;
            } else if (!Character.isDigit(expression.charAt(i))) {
                main.add(new Operator(expression.charAt(i)), depth);
            }
        }
        truthTable = new TruthTable(main);
    }

    private String format(String str) {
        return str.toUpperCase()
                .replaceAll("\\s", "")
                .replaceAll("\\[\\{", "(")
                .replaceAll("]}", ")")
                .replaceAll("NOT|[˜~¬]", "!")
                .replaceAll("XNOR", "⊙")
                .replaceAll("NOR|[⊽]]", "↓")
                .replaceAll("XOR|[⊻]]", "⊕")
                .replaceAll("OR|[∨∥]", "+")
                .replaceAll("NAND|[⊼]]", "↑")
                .replaceAll("AND|[∧·.&]", "*")
                .replaceAll("(?<=[\\w)])(?=[A-Z(!])", "*");
    }

    private void validate(String str) throws IllegalArgumentException {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        if ((str.length() - str.replace("(", "").length()) != (str.length() - str.replace(")", "").length())) {
            throw new IllegalArgumentException("Unclosed parenthesis");
        }
        if (!Character.isAlphabetic(str.charAt(0)) && str.charAt(0) != '(' && str.charAt(0) != '!') {
            throw new IllegalArgumentException("Malformed expression");
        }
    }

    public boolean isEquivalentWith(Expression that) {
        return this.truthTable.equals(that.truthTable);
    }

    public String getExpression() {
        return expression;
    }

    public TruthTable getTruthTable() {
        return truthTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return Objects.equals(expression, that.expression) && Objects.equals(truthTable, that.truthTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, truthTable);
    }

    @Override
    public String toString() {
        return expression.replaceAll("\\*", "") +
                "\n" +
                truthTable;
    }
}
