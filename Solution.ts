
function findRepeatedDnaSequences(input: string): string[] {
    this.PRIME_BASE = 5;
    this.SEQUENCE_SIZE = 10;
    this.MODULO_ROLLING_HASH = Math.pow(10, 9) + 7;
    this.LEADING_POWER_OF_PRIME_BASE = Math.pow(this.PRIME_BASE, this.SEQUENCE_SIZE - 1);

    if (input.length <= this.SEQUENCE_SIZE) {
        return new Array();
    }

    const foundDnaSequences = new Set();
    const addedToResultsRepeatedDnaSequences = new Set();
    const resultsRepeatedDnaSequences = new Array();

    let rollingHash = initializeRollingHash(input);
    foundDnaSequences.add(rollingHash);

    for (let i = this.SEQUENCE_SIZE; i < input.length; ++i) {
        rollingHash = shrinkRollingHash(rollingHash, input.codePointAt(i - this.SEQUENCE_SIZE));
        rollingHash = expandRollingHash(rollingHash, input.codePointAt(i));

        if (foundDnaSequences.has(rollingHash) && !addedToResultsRepeatedDnaSequences.has(rollingHash)) {
            resultsRepeatedDnaSequences.push(input.substring(i - this.SEQUENCE_SIZE + 1, i + 1));
            addedToResultsRepeatedDnaSequences.add(rollingHash);
        } else {
            foundDnaSequences.add(rollingHash);
        }
    }

    return resultsRepeatedDnaSequences;
};

function initializeRollingHash(input: string): number {
    let rollingHash = 0;
    for (let i = 0; i < this.SEQUENCE_SIZE; ++i) {
        rollingHash = expandRollingHash(rollingHash, input.codePointAt(i));
    }
    return rollingHash;
}

function expandRollingHash(rollingHash: number, characterASCII: number): number {
    return (rollingHash * this.PRIME_BASE + characterASCII) % this.MODULO_ROLLING_HASH;
}

function shrinkRollingHash(rollingHash: number, characterASCII: number): number {
    return (rollingHash - this.LEADING_POWER_OF_PRIME_BASE * characterASCII) % this.MODULO_ROLLING_HASH;
}
