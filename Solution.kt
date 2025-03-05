
import kotlin.math.pow

class Solution {

    private companion object {
        const val PRIME_BASE = 5
        const val SEQUENCE_SIZE = 10
        val MODULO_ROLLING_HASH = (10.0).pow(9.0).toInt() + 7
        val LEADING_POWER_OF_PRIME_BASE: Long = (PRIME_BASE.toDouble()).pow(SEQUENCE_SIZE - 1).toLong()
    }

    fun findRepeatedDnaSequences(input: String): List<String> {
        if (input.length <= SEQUENCE_SIZE) {
            return ArrayList<String>()
        }

        val foundDnaSequences = HashSet<Long>()
        val addedToResultsRepeatedDnaSequences = HashSet<Long>()
        val resultsRepeatedDnaSequences = ArrayList<String>()


        var rollingHash: Long = initializeRollingHash(input)
        foundDnaSequences.add(rollingHash)

        for (i in SEQUENCE_SIZE..<input.length) {
            rollingHash = shrinkRollingHash(rollingHash, input[i - SEQUENCE_SIZE])
            rollingHash = expandRollingHash(rollingHash, input[i])

            if (!foundDnaSequences.add(rollingHash) && addedToResultsRepeatedDnaSequences.add(rollingHash)) {
                resultsRepeatedDnaSequences.add(input.substring(i - SEQUENCE_SIZE + 1, i + 1))
            }
        }

        return resultsRepeatedDnaSequences
    }

    private fun initializeRollingHash(input: String): Long {
        var rollingHash: Long = 0
        for (i in 0..<SEQUENCE_SIZE) {
            rollingHash = expandRollingHash(rollingHash, input[i])
        }
        return rollingHash
    }

    private fun expandRollingHash(rollingHash: Long, character: Char): Long {
        return (rollingHash * PRIME_BASE + character.code) % MODULO_ROLLING_HASH
    }

    private fun shrinkRollingHash(rollingHash: Long, character: Char): Long {
        return (rollingHash - LEADING_POWER_OF_PRIME_BASE * character.code) % MODULO_ROLLING_HASH
    }
}
