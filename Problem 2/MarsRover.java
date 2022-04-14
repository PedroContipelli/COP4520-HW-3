import java.util.*;
import java.util.concurrent.atomic.*;

public class MarsRover
{
	public static void main(String[] args) throws InterruptedException
	{
		// Get user input
		Scanner scan = new Scanner(System.in);
		System.out.print("Run for how many (simulated) hours? ");
		int totalHours = scan.nextInt();
		System.out.println();
		scan.close();

		// Number of threads
		int numSensors = 8;
		Thread[] sensors = new Thread[numSensors];
		
		// Shared memory space
		AtomicInteger minutesElapsed = new AtomicInteger(0);
		AtomicBoolean done = new AtomicBoolean(false);
		AtomicInteger index = new AtomicInteger(0);
		int[][] readings = new int[60][8];
		
		// Start execution timer
		long startTime = System.currentTimeMillis();
		
		// Declare and initialize all threads
		for (int i = 0; i < sensors.length; i++)
			sensors[i] = new SensorThread(i, minutesElapsed, done, readings, index);
		
		// Begin running
		for (int i = 0; i < sensors.length; i++)
			sensors[i].start();
		
		// Simulate each hour
		for (int hour = 0; hour < totalHours; hour++)
		{
			while (minutesElapsed.get() < 60)
			{				
				Thread.sleep(25);
				
				if (minutesElapsed.get() == 59)
				{
					minutesElapsed.set(0);
					break;
				}
				
				minutesElapsed.incrementAndGet();
			}
			
			System.out.println("Hour " + (hour + 1));
			System.out.println("=============");
			report(readings);
		}
		
		// Let threads know simulation is over
		done.set(true);
		
		// Wait until all threads have finished execution
		for (int i = 0; i < sensors.length; i++)
			sensors[i].join();
		
		// End execution timer
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		
		System.out.printf("\nTotal execution time: %dms\n", executionTime);
	}
	
	public static void report(int[][] arr)
	{
		int[] mins = new int[60];
		int[] maxes = new int[60];
		
		for (int i = 0; i < 60; i++)
		{
			mins[i] = min(arr[i]);
			maxes[i] = max(arr[i]);
		}
		
		int start = 0;
		int maxDist = 0;
		
		for (int i = 0; i < 50; i++)
		{
			int max = max(Arrays.copyOfRange(maxes, i, i + 10));
			int min = min(Arrays.copyOfRange(mins, i, i + 10));
			
			if (max - min > maxDist)
			{
				maxDist = max - min;
				start = i;
			}
		}
		
		System.out.print("Interval with largest temperature difference: Minutes " + start + " to " + (start+9));
		System.out.println(" with a temperature difference of " + maxDist + ".");
		
		int[] nums = new int[480];
		
		for (int i = 0; i < 60; i++)
			for (int j = 0; j < 8; j++)
				nums[i*8 + j] = arr[i][j];
		
		Arrays.sort(nums);
		
		System.out.println("Top 5 highest: " + Arrays.toString(Arrays.copyOfRange(nums, 475, 480)));
		System.out.println("Top 5 lowest: " + Arrays.toString(Arrays.copyOfRange(nums, 0, 5)) + "\n");
	}
	
	public static int max(int[] nums)
	{
		int max = -200;
		
		for (int i = 0; i < nums.length; i++)
			max = Math.max(max, nums[i]);
		
		return max;
	}
	
	public static int min(int[] nums)
	{
		int min = 200;
		
		for (int i = 0; i < nums.length; i++)
			min = Math.min(min, nums[i]);
		
		return min;
	}
	
	
	public static void print(int[][] arr)
	{
		for (int[] row : arr)
			System.out.println(Arrays.toString(row));
		
		System.out.println("\n\n");
	}
}