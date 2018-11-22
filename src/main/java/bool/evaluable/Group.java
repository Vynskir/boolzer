package bool.evaluable;

import java.util.*;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Group implements Evaluable {
    private final List<Group> groups = new ArrayList<>();
    private final List<Variable> variables = new ArrayList<>();
    private boolean not;
    private Operator operator;
    private Evaluable evaluable;
    private List<Operation> operations = new ArrayList<>();

    public Group() {
        this(false);
    }

    public Group(boolean not) {
        this.not = not;
    }

    public void add(Group group, int depth) {
        if (depth == 1) {
            if (operator != null) operations.add(new Operation(operator, evaluable, group));
            evaluable = group;
            groups.add(group);
        } else {
            groups.get(groups.size() - 1).add(group, depth - 1);
        }
    }

    public void add(Variable variable, int depth) {
        variables.add(variable);

        if (depth == 0) {
            if (operator != null) operations.add(new Operation(operator, evaluable, variable));
            evaluable = variable;
        } else {
            groups.get(this.groups.size() - 1).add(variable, depth - 1);
        }
    }

    public void add(Operator operator, int depth) {
        if (depth == 0) {
            this.operator = operator;
        } else {
            groups.get(groups.size() - 1).add(operator, depth - 1);
        }
    }

    private void mergeOperations() {
        operations.sort(Comparator.reverseOrder());

        boolean overlapped;
        do {
            overlapped = false;

            for (int i = 0; i < operations.size() - 1; i++) {
                for (int j = i + 1; j < operations.size(); j++) {
                    if (operations.get(i).overlapsWith(operations.get(j))) {
                        overlapped = true;                    }
                }
            }
        } while (overlapped);
    }

    @Override
    public boolean evaluate() {
        if (operations.isEmpty() && !groups.isEmpty()) return groups.get(0).evaluate();

        mergeOperations();

        boolean evaluation = operations.isEmpty() ? evaluable.evaluate() : operations.get(0).evaluate();
        return not != evaluation;
    }

    public void setValues(Variable v, boolean b) {
        for (Variable variable : variables) {
            if (variable.getLabel().equals(v.getLabel())) variable.setValue(b);
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groups, group.groups) &&
                Objects.equals(variables, group.variables) &&
                Objects.equals(operations, group.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups, variables, operations);
    }

    @Override
    public String toString() {
        return not ? "!" + operations.toString() : operations.toString();
    }
}
