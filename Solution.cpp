
#include <string>
#include <vector>
#include <string_view>
#include <unordered_set>
using namespace std;

class Solution {

    static const int PRIME_BASE = 5;
    static const int SEQUENCE_SIZE = 10;
    inline static const int MODULO_ROLLING_HASH = pow(10, 9) + 7;
    inline static const long long LEADING_POWER_OF_PRIME_BASE = pow(PRIME_BASE, SEQUENCE_SIZE - 1);

public:
    vector<string> findRepeatedDnaSequences(const string& input) const {
        if (input.length() <= SEQUENCE_SIZE) {
            return vector<string>();
        }

        unordered_set<long long> foundDnaSequences;
        unordered_set<long long> addedToResultsRepeatedDnaSequences;
        vector<string> resultsRepeatedDnaSequences;

        long long rollingHash = initializeRollingHash(input);
        foundDnaSequences.insert(rollingHash);

        for (int i = SEQUENCE_SIZE; i < input.length(); ++i) {
            rollingHash = shrinkRollingHash(rollingHash, input[i - SEQUENCE_SIZE]);
            rollingHash = expandRollingHash(rollingHash, input[i]);

            if (foundDnaSequences.contains(rollingHash) && !addedToResultsRepeatedDnaSequences.contains(rollingHash)) {
                resultsRepeatedDnaSequences.push_back(input.substr(i - SEQUENCE_SIZE + 1, SEQUENCE_SIZE));
                addedToResultsRepeatedDnaSequences.insert(rollingHash);
            }
            else {
                foundDnaSequences.insert(rollingHash);
            }
        }

        return resultsRepeatedDnaSequences;
    }

private:
    long long initializeRollingHash(string_view input) const {
        long long rollingHash = 0;
        for (int i = 0; i < SEQUENCE_SIZE; ++i) {
            rollingHash = expandRollingHash(rollingHash, input[i]);
        }
        return rollingHash;
    }

    long long expandRollingHash(long long rollingHash, char character) const {
        return (rollingHash * PRIME_BASE + character) % MODULO_ROLLING_HASH;
    }

    long long shrinkRollingHash(long long rollingHash, char character) const {
        return (rollingHash - LEADING_POWER_OF_PRIME_BASE * character) % MODULO_ROLLING_HASH;
    }
};
