// IMPLEMENTATION BASED ON "The Art of Multiprocessor Programming, Revised Reprint"

import java.util.concurrent.atomic.*;

class Node
{
	int key;
	AtomicMarkableReference<Node> next;
	
	public Node(int key)
	{
		this.key = key;
		this.next = new AtomicMarkableReference<Node>(null, false);
	}
}