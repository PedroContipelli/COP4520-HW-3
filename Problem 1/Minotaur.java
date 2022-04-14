import java.util.*;
import java.util.concurrent.atomic.*;

public class Minotaur
{
	public static void main(String[] args) throws InterruptedException
	{
		int numServants = 4;
		Thread[] servants = new Thread[numServants];
		
		// Unordered bag of presents 1 - 500k
		ArrayList<Integer> bag = new ArrayList<>(500_000);
		
		for (int i = 1; i <= 500_000; i++)
			bag.add(i);
		
		Collections.shuffle(bag);
		
		// Counters for adding presents to bag and removing from chain
		AtomicInteger indexInBag = new AtomicInteger(0);
		AtomicInteger cardsWritten = new AtomicInteger(0);
		LockFreeList chain = new LockFreeList();
		
		// Start execution timer
		long startTime = System.currentTimeMillis();
		
		// Declare and initialize all threads
		for (int i = 0; i < servants.length; i++)
			servants[i] = new ServantThread(bag, indexInBag, cardsWritten, chain);
		
		// Begin running
		for (int i = 0; i < servants.length; i++)
			servants[i].start();
		
		// Wait until all threads have finished execution
		for (int i = 0; i < servants.length; i++)
			servants[i].join();
		
		// End execution timer
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		
		System.out.println("\nAll 500,000 presents and 'Thank You' letters written.");
		System.out.printf("Execution time: %dms\n", executionTime);
	}
}
