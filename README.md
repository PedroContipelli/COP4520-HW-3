# COP4520-HW-3

### Problem 1

What could have gone wrong is that while one servant unlinked the predecessor to add a new gift to the chain, but had not yet finished linking it to the next in the chain, another servant came and removed that present at the same time. This causes us to completely lose the part of the chain that came after that present. If this happens even once, at the end the servants will end up having counted more presents than written thank you notes.

Yes, we can help the minotaur by using a wait-free concurrent linked list implementation with thread-safe guarantees for the chain. I chose to implement LockFreeList from Chapter 9 of "The Art of Multiprocessor Programming, Revised Reprint"

##### Execution time for processing 500,000 presents and 'Thank You' letters: 115ms

### Problem 2



