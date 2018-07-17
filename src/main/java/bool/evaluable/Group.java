package bool.evaluable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements Evaluable {
    private boolean not;

    private Operator operator;
    private Evaluable evaluable;

    private List<Group> groups = new ArrayList<>();
    private List<Variable> variables = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();

    public Group(boolean inverted) {
        this.not = inverted;
    }

    public void add(Group group, int depth) {
        if (depth == 1) {
            if (this.operator != null) {
                operations.add(new Operation(this.operator, evaluable, group));
            }
            this.evaluable = group;
            this.groups.add(group);
        } else {
            this.groups.get(groups.size() - 1).add(group, depth - 1);
        }
    }

    public void add(Variable variable, int depth) {
        this.variables.add(variable);

        if (depth == 0) {
            if (this.operator != null) {
                operations.add(new Operation(this.operator, evaluable, variable));
            }
            this.evaluable = variable;
        } else {

            this.groups.get(this.groups.size() - 1).add(variable, depth - 1);
        }
    }

    public void add(Operator operator, int depth) {
        if (depth == 0) {
            this.operator = operator;
        } else {
            this.groups.get(groups.size() - 1).add(operator, depth - 1);
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
