package math;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thibault Robin
 * @version 1.0
 */
public class Permutation {
    private final List<String> values;

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
