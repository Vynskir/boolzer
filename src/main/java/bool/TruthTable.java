package bool;

import bool.evaluable.Group;
import bool.evaluable.Variable;
import math.Permutation;

import java.util.*;

public class TruthTable {

    private List<Variable> header = new ArrayList<>();
    private List<List<Character>> table = new ArrayList<>();

    public TruthTable(Group group) {
        header.addAll(new HashSet<>(group.getVariables()));
        header.sort(Comparator.naturalOrder());
        header.add(new Variable("Out", false));

        List<String> permutations = new Permutation(header.size() - 1).getValues();
        for (String permutation : permutations) {
            List<Character> row = new ArrayList<>();

            for (int i = 0; i < permutation.length(); i++) {
                if (permutation.charAt(i) == '1') {
                    group.setValues(header.get(i), true);
                } else {
                    group.setValues(header.get(i), false);
                }
                row.add(permutation.charAt(i));
            }
            if (group.evaluate()) row.add('1');
            else row.add('0');
            table.add(row);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruthTable that = (TruthTable) o;
        return Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Variable variable : header) {
            sb.append(variable.getName());
            sb.append("\t");
        }
        sb.append("\n");
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
