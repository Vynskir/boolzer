package bool.evaluable;

import java.util.Objects;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Variable implements Evaluable, Comparable<Variable> {
    private final boolean not;
    private final String name;
    private boolean value;

    public Variable(char name, boolean not) {
        this(Character.toString(name), not);
    }

    public Variable(String name, boolean not) {
        this.name = name;
        this.not = not;
    }

    @Override
    public boolean evaluate() {
        return not != value;
    }

    public String getName() {
        return name;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public int compareTo(Variable that) {
        return this.name.compareTo(that.name);
    }

    @Override
    public String toString() {
        if (not) return "NOT " + name;
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
