package bool.expression;

import bool.evaluable.Group;
import bool.evaluable.Operator;
import bool.evaluable.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                main.add(new Variable(expression.charAt(i), not), depth);
                not = false;
            } else if (expression.charAt(i) == '(') {
                depth++;
                main.add(new Group(not), depth);
                not = false;
            } else if (expression.charAt(i) == ')') {
                depth--;
            } else if (expression.charAt(i) == '!') {
                not = !not;
            } else {
                main.add(new Operator(expression.charAt(i)), depth);
            }
        }
        truthTable = new TruthTable(main);
    }

    private String format(String str) {
        return str.toUpperCase()
                .replaceAll("\\s", "")
                .replaceAll("NOT|NON|[˜~¬]", "!")
                .replaceAll("XNOR", "⊙")
                .replaceAll("NOR|[⊽]]", "↓")
                .replaceAll("XOR|[⊻]]", "⊕")
                .replaceAll("OR|OU|[∨∥]", "+")
                .replaceAll("NAND|[⊼]]", "↑")
                .replaceAll("AND|ET|[∧·.&]", "*")
                .replaceAll("(?<=[\\w)])(?=[A-Z(!])", "*");
    }

    private void validate(String str) throws IllegalArgumentException {
        if (str.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (str.contains("()")) {
            throw new IllegalArgumentException("Empty parenthesis");
        }
        if ((str.length() - str.replace("(", "").length()) != (str.length() - str.replace(")", "").length())) {
            throw new IllegalArgumentException("Unclosed parenthesis");
        }
        Matcher matcher = Pattern.compile("[^A-Z()!⊙↓⊕+↑*]").matcher(str);
        List<String> characters = new ArrayList<>();
        while (matcher.find()) {
            characters.add(String.valueOf(str.charAt(matcher.start())));
        }
        if (characters.size() == 1) {
            throw new IllegalArgumentException("Invalid character: \"" + characters.get(0) + "\"");
        } else if (characters.size() > 1) {
            throw new IllegalArgumentException("Invalid characters: \"" + String.join(", ", characters) + "\"");
        }
        if (Pattern.compile("[⊙↓⊕+↑*](?=[^A-Z()!])|(?<=[^A-Z()])[⊙↓⊕+↑*]|(?<=^)[⊙↓⊕+↑*]|[!⊙↓⊕+↑*](?=$)").matcher(str).find()) {
            throw new IllegalArgumentException("Malformed expression");
        }
    }

    public boolean isEquivalentWith(Expression that) {
        return this.truthTable.equals(that.truthTable);
    }

    public String getExpression() {
        return expression.replaceAll("\\*", "");
    }

    public TruthTable getTruthTable() {
        return truthTable;
    }

    public int size() {
        String gates = "!⊙↓⊕+↑*";

        int size = 0;
        for (char c : expression.toCharArray()) {
            if (gates.indexOf(c) != -1) size++;
        }
        return size;
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
        return expression +
                "\n" +
                truthTable;
    }
}
