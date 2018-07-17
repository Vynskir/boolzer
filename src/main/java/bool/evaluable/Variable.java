package bool.evaluable;

import java.util.Objects;

public class Variable implements Evaluable, Comparable<Variable> {
    private boolean value;
    private boolean not;

    private String name;

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

    @Override
    public int compareTo(Variable that) {
        return this.name.compareTo(that.name);
    }
}
