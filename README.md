# Repeated-DNA-Sequences
Challenge at LeetCode.com. Tags: Rolling Hash, String, Sliding Window.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------

The problem can be solved just with hash tables of strings, in  fewer lines of code, and due to the small size of <br/>the input, 
it would be as fast as a more efficient solution, such as Rolling Hash. However, the purpose here is to demonstrate Rolling Hash. 

---------------------------------------------------------------------------------------------------------------------------------------------------------------------

Rolling Hash is a 64-bit integer because during the calculations, and before taking modulo Math.pow(10, 9) + 7), 
it might temporarily exceed the boundary of a 32-bit integer. However, after taking modulo Math.pow(10, 9) + 7), the value of Rolling Hash
(obviously!!) is within the limits of a 32-bit integer. That said, the HashSets are for 64-bit integers in order to avoid constant
casting from 64-bit integer to 32-bit integer. 

This, of course, concerns those of the languages in the presented solutions that have distinct in-built types for 32-bit and 64-bit integers, namely, 
Java, C++, C#, Kotlin and Golang. As for JavaScript and TypeScript, the solution just uses their in-built number, which is implemented as double-precision 64-bit binary format IEEE 754.
