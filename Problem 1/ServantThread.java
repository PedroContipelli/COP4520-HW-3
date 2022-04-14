import java.util.concurrent.atomic.*;
import java.util.*;

public class ServantThread extends Thread
{
	ArrayList<Integer> bag;
	AtomicInteger addPointer;
	AtomicInteger removePointer;
	LockFreeList chain;
	
	public ServantThread(ArrayList<Integer> bag, AtomicInteger addPointer, AtomicInteger removePointer, LockFreeList chain)
	{
		this.bag = bag;
		this.addPointer = addPointer;
		this.removePointer = removePointer;
		this.chain = chain;
	}
	
	public void run()
	{
		// LOOP until all presents have been added to the chain, and all 'Thank You' letters have been written
		while (addPointer.get() < bag.size() || removePointer.get() < bag.size())
		{
			// ADD: Try to add a present to the chain
			try
			{
				int present = bag.get(addPointer.getAndIncrement());
				chain.add(present);
			} catch (Exception e) {}
			
			// REMOVE: Try removing an existing present from the chain and writing a thank you letter
			if (removePointer.get() < addPointer.get() - 3)
			{
				try
				{
					int writeCard = bag.get(removePointer.getAndIncrement());
					chain.remove(writeCard);
				} catch (Exception e) {}
			}
			
			// CONTAINS: With a small random probability, check if a present (that was recently added) is still in the chain
			if (Math.random() < 0.0001)
			{
				try
				{
					int numCheck = bag.get(addPointer.get() - (int)(Math.random() * 6.5 + 1));
					
					if (chain.contains(numCheck))
						System.out.println("Chain contains " + numCheck);
					else
						System.out.println("Chain does not contain " + numCheck);
				} catch (Exception e) {}
			}
			
		}
	}

}
