
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    private static final int PRIME_BASE = 5;
    private static final int SEQUENCE_SIZE = 10;
    private static final int MODULO_ROLLING_HASH = (int) Math.pow(10, 9) + 7;
    private static final long LEADING_POWER_OF_PRIME_BASE = (long) Math.pow(PRIME_BASE, SEQUENCE_SIZE - 1);

    public List<String> findRepeatedDnaSequences(String input) {
        if (input.length() <= SEQUENCE_SIZE) {
            return new ArrayList<>();
        }

        Set<Long> foundDnaSequences = new HashSet<>();
        Set<Long> addedToResultsRepeatedDnaSequences = new HashSet<>();
        List<String> resultsRepeatedDnaSequences = new ArrayList<>();

        char[] inputAsArray = input.toCharArray();
        long rollingHash = initializeRollingHash(inputAsArray);
        foundDnaSequences.add(rollingHash);

        for (int i = SEQUENCE_SIZE; i < inputAsArray.length; ++i) {
            rollingHash = shrinkRollingHash(rollingHash, inputAsArray[i - SEQUENCE_SIZE]);
            rollingHash = expandRollingHash(rollingHash, inputAsArray[i]);

            if (!foundDnaSequences.add(rollingHash) && addedToResultsRepeatedDnaSequences.add(rollingHash)) {
                resultsRepeatedDnaSequences.add(input.substring(i - SEQUENCE_SIZE + 1, i + 1));
            }
        }

        return resultsRepeatedDnaSequences;
    }

    private long initializeRollingHash(char[] inputAsArray) {
        long rollingHash = 0;
        for (int i = 0; i < SEQUENCE_SIZE; ++i) {
            rollingHash = expandRollingHash(rollingHash, inputAsArray[i]);
        }
        return rollingHash;
    }

    private long expandRollingHash(long rollingHash, char character) {
        return (rollingHash * PRIME_BASE + character) % MODULO_ROLLING_HASH;
    }

    private long shrinkRollingHash(long rollingHash, char character) {
        return (rollingHash - LEADING_POWER_OF_PRIME_BASE * character) % MODULO_ROLLING_HASH;
    }
}
