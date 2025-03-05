
using System;
using System.Collections.Generic;

public class Solution
{
    private static readonly int PRIME_BASE = 5;
    private static readonly int SEQUENCE_SIZE = 10;
    private static readonly int MODULO_ROLLING_HASH = (int)Math.Pow(10, 9) + 7;
    private static readonly long LEADING_POWER_OF_PRIME_BASE = (long)Math.Pow(PRIME_BASE, SEQUENCE_SIZE - 1);

    public IList<string> FindRepeatedDnaSequences(string input)
    {
        if (input.Length <= SEQUENCE_SIZE)
        {
            return new List<string>();
        }

        HashSet<long> foundDnaSequences = new HashSet<long>();
        HashSet<long> addedToResultsRepeatedDnaSequences = new HashSet<long>();
        IList<string> resultsRepeatedDnaSequences = new List<string>();

        long rollingHash = InitializeRollingHash(input);
        foundDnaSequences.Add(rollingHash);

        for (int i = SEQUENCE_SIZE; i < input.Length; ++i)
        {
            rollingHash = ShrinkRollingHash(rollingHash, input[i - SEQUENCE_SIZE]);
            rollingHash = ExpandRollingHash(rollingHash, input[i]);

            if (!foundDnaSequences.Add(rollingHash) && addedToResultsRepeatedDnaSequences.Add(rollingHash))
            {
                resultsRepeatedDnaSequences.Add(input.Substring(i - SEQUENCE_SIZE + 1, SEQUENCE_SIZE));
            }
        }

        return resultsRepeatedDnaSequences;
    }

    private long InitializeRollingHash(string input)
    {
        long rollingHash = 0;
        for (int i = 0; i < SEQUENCE_SIZE; ++i)
        {
            rollingHash = ExpandRollingHash(rollingHash, input[i]);
        }
        return rollingHash;
    }

    private long ExpandRollingHash(long rollingHash, char character)
    {
        return (rollingHash * PRIME_BASE + character) % MODULO_ROLLING_HASH;
    }

    private long ShrinkRollingHash(long rollingHash, char character)
    {
        return (rollingHash - LEADING_POWER_OF_PRIME_BASE * character) % MODULO_ROLLING_HASH;
    }
}
