# COP4520-HW-3

### Problem 1

Compile with: **javac Minotaur.java**  
Run with: **java Minotaur**

What could have gone wrong is that while one servant unlinked the predecessor to add a new gift to the chain, but had not yet finished linking it to the next in the chain, another servant came and removed that present at the same time. This causes us to completely lose the part of the chain that came after that present. If this happens even once, at the end the servants will end up having counted more presents than written thank you notes.

Yes, we can help the minotaur by using a wait-free concurrent linked list implementation with thread-safe guarantees for the chain. I chose to implement LockFreeList from Chapter 9 of "The Art of Multiprocessor Programming, Revised Reprint"

Design Explanation:
"As described in detail in Pragma 9.8.1, an AtomicMarkableReference<T> object encapsulates both a reference to an object of type T and a Boolean mark. These fields can be atomically updated, either together or individually. We make each node’s next field an AtomicMarkableReference<Node>. Thread A logically removes currA by setting the mark bit in the node’s next field, and shares the physical removal with other threads performing add() or remove(): as each thread traverses the list, it cleans up the list by physically removing (using compareAndSet()) any marked nodes it encounters. In other words, threads performing add() and remove() do not traverse marked nodes, they remove them before continuing. The contains() method remains the same as in the LazyList algorithm, traversing all nodes whether they are marked or not, and testing if an item is in the list based on its key and mark."

#### Execution time for processing 500,000 presents and 'Thank You' letters: 315ms

### Problem 2

Compile with: **javac MarsRover.java**  
Run with: **java MarsRover**

### Sources:
Maurice Herlihy and Nir Shavit. 2008. The Art of Multiprocessor Programming. Morgan Kaufmann Publishers Inc., San Francisco, CA, USA.
