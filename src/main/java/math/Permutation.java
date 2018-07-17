package math;

import java.util.ArrayList;
import java.util.List;

public class Permutation {

    private List<String> values;

    public Permutation(int size) {
        values = compute(size, "");
    }

    private List<String> compute(int size, String recursive) {
        List<String> permutations = new ArrayList<>();

        if (recursive.length() == size) {
            permutations.add(recursive);

            return permutations;
        }
        permutations.addAll(compute(size, recursive + "0"));
        permutations.addAll(compute(size, recursive + "1"));

        return permutations;
    }

    public List<String> getValues() {
        return values;
    }
}
