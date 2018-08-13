package bool.expression;

import bool.evaluable.Group;
import bool.evaluable.Variable;
import math.Permutation;

import java.util.*;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class TruthTable {
    private final List<Variable> variables = new ArrayList<>();
    private final List<List<Character>> table = new ArrayList<>();

    public TruthTable(Group group) {
        variables.addAll(new HashSet<>(group.getVariables()));
        variables.sort(Comparator.naturalOrder());

        List<String> permutations = new Permutation(variables.size()).getValues();
        for (String permutation : permutations) {
            List<Character> row = new ArrayList<>();

            for (int i = 0; i < permutation.length(); i++) {
                group.setValues(variables.get(i), permutation.charAt(i) == '1');
                row.add(permutation.charAt(i));
            }
            if (group.evaluate()) {
                row.add('1');
            } else {
                row.add('0');
            }
            table.add(row);
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public List<List<Character>> getTable() {
        return table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruthTable that = (TruthTable) o;
        return Objects.equals(variables, that.variables) &&
                Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variables, table);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Variable variable : variables) {
            sb.append(variable.getLabel());
            sb.append("\t");
        }
        sb.append("Out\n");
        for (List<Character> row : table) {
            for (char c : row) {
                sb.append(c);
                sb.append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
