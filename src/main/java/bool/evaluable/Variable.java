package bool.evaluable;

import java.util.Objects;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Variable implements Evaluable, Comparable<Variable> {
    private final boolean not;
    private final String label;
    private boolean value;

    public Variable(char label, boolean not) {
        this(Character.toString(label), not);
    }

    public Variable(String label, boolean not) {
        this.label = label;
        this.not = not;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean evaluate() {
        return not != value;
    }

    @Override
    public int compareTo(Variable that) {
        return this.label.compareTo(that.label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return label.equals(variable.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        if (not) return "NOT " + label;
        return label;
    }
}
