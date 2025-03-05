
package main

import "math"

const PRIME_BASE = 5
const SEQUENCE_SIZE = 10

var MODULO_ROLLING_HASH = int32(math.Pow(10, 9) + 7)
var LEADING_POWER_OF_PRIME_BASE = int64(math.Pow(PRIME_BASE, SEQUENCE_SIZE - 1))

func findRepeatedDnaSequences(input string) []string {
    if len(input) <= SEQUENCE_SIZE {
        return []string{}
    }

    foundDnaSequences := NewHashSet()
    addedToResultsRepeatedDnaSequences := NewHashSet()
    resultsRepeatedDnaSequences := make([]string, 0)

    var rollingHash int64 = initializeRollingHash(input)
    foundDnaSequences.Add(rollingHash)

    for i := SEQUENCE_SIZE; i < len(input); i++ {
        rollingHash = shrinkRollingHash(rollingHash, input[i - SEQUENCE_SIZE])
        rollingHash = expandRollingHash(rollingHash, input[i])

        if foundDnaSequences.Contains(rollingHash) && !addedToResultsRepeatedDnaSequences.Contains(rollingHash) {
            resultsRepeatedDnaSequences = append(resultsRepeatedDnaSequences, input[i - SEQUENCE_SIZE + 1 : i + 1])
            addedToResultsRepeatedDnaSequences.Add(rollingHash)
        } else {
            foundDnaSequences.Add(rollingHash)
        }
    }

    return resultsRepeatedDnaSequences
}

func initializeRollingHash(input string) int64 {
    var rollingHash int64 = 0
    for i := range SEQUENCE_SIZE {
        rollingHash = expandRollingHash(rollingHash, input[i])
    }
    return rollingHash
}

func expandRollingHash(rollingHash int64, character byte) int64 {
    return (rollingHash * int64(PRIME_BASE) + int64(character)) % int64(MODULO_ROLLING_HASH)
}

func shrinkRollingHash(rollingHash int64, character byte) int64 {
    return (rollingHash - LEADING_POWER_OF_PRIME_BASE * int64(character)) % int64(MODULO_ROLLING_HASH)
}

type HashSet struct {
    conainer map[int64]bool
}

func NewHashSet() HashSet {
    return HashSet{conainer: map[int64]bool{}}
}

func (this *HashSet) Contains(element int64) bool {
    return this.conainer[element]
}

func (this *HashSet) Add(element int64) {
    this.conainer[element] = true
}

func (this *HashSet) Remove(element int64) {
    delete(this.conainer, element)
}
