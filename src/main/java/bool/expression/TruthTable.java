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

    private List<Variable> comparableVariables;
    private List<List<Character>> comparableTable;

    public TruthTable(Group group) {
        this(group, group.getVariables());
    }

    private TruthTable(Group group, List<Variable> variables) {
        this.variables.addAll(new HashSet<>(variables));
        this.variables.sort(Comparator.naturalOrder());

        List<String> permutations = new Permutation(this.variables.size()).getValues();
        for (String permutation : permutations) {

            List<Character> row = new ArrayList<>();
            for (int i = 0; i < permutation.length(); i++) {
                group.setValues(this.variables.get(i), permutation.charAt(i) == '1');
                row.add(permutation.charAt(i));
            }
            row.add(group.evaluate() ? '1' : '0');
            table.add(row);
        }
        comparableVariables = new ArrayList<>(this.variables);
        comparableTable = new ArrayList<>(table);
    }

    public void removeRedundancies(Group group) {
        List<Variable> redundancies = new ArrayList<>();

        for (int i = 0; i < variables.size(); i++) {
            int lines = 0;
            int count = 0;

            for (List<Character> row : table) {
                if (row.get(variables.size()) == '1') {
                    lines++;

                    if (row.get(i) == '1') {
                        count++;
                    }
                }
            }
            if (lines > 1 && count == lines/2) {
                redundancies.add(variables.get(i));
            }
        }
        if (!redundancies.isEmpty()) {
            List<Variable> newVariables = new ArrayList<>(variables);

            for (Variable redundancy : redundancies) {
                newVariables.remove(redundancy);
            }
            TruthTable other = new TruthTable(group, newVariables);
            comparableVariables = other.comparableVariables;
            comparableTable = other.comparableTable;
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
        return Objects.equals(comparableVariables, that.comparableVariables) &&
                Objects.equals(comparableTable, that.comparableTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparableVariables, comparableTable);
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
