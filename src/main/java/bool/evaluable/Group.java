package bool.evaluable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Group implements Evaluable {
    private final boolean not;
    private final List<Group> groups = new ArrayList<>();
    private final List<Variable> variables = new ArrayList<>();
    private Operator operator;
    private Evaluable evaluable;
    private List<Operation> operations = new ArrayList<>();

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

    private List<Operation> mergeOperations(List<Operation> operations) {
        if (operations.size() == 1) return operations;

        List<Operation> merges = new ArrayList<>();

        boolean merged = false;
        for (int i = 0; i < operations.size() - 1; i++) {
            for (int j = 1; j < operations.size(); j++) {
                if (operations.get(i).overlapsWith(operations.get(j))) {
                    merged = true;
                }
                merges.add(operations.get(i));
            }
        }
        return merged ? mergeOperations(merges) : merges;
    }

    @Override
    public boolean evaluate() {
        int i = 0;
        while (operations.isEmpty() && i < groups.size()) {
            operations = groups.get(i).operations;
            i++;
        }
        operations.sort(Collections.reverseOrder());
        List<Operation> merges = mergeOperations(operations);

        boolean value;
        if (merges.isEmpty()) value = variables.get(0).evaluate();
        else value = merges.get(0).evaluate();
        return not != value;
    }

    public void setValues(Variable v, boolean b) {
        for (Variable variable : variables) {
            if (variable.getName().equals(v.getName())) variable.setValue(b);
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        if (not) return "NOT " + operations.toString();
        return operations.toString();
    }

}
