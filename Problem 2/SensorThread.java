import java.util.concurrent.atomic.*;

public class SensorThread extends Thread
{
	int id;
	AtomicInteger minutesElapsed;
	AtomicBoolean done;
	int[][] readings;
	AtomicInteger index;
	
	public SensorThread(int id, AtomicInteger minutesElapsed, AtomicBoolean done, int[][] readings, AtomicInteger index)
	{
		this.id = id;
		this.minutesElapsed = minutesElapsed;
		this.done = done;
		this.readings = readings;
		this.index = index;
	}
	
	public void run()
	{
		// Running simulation
		while (!done.get())
		{
			// Take sensor reading
			int minute = minutesElapsed.get();
			
			int sensorReading = (int)Math.floor(Math.random() * 171 - 100);
			readings[minute][id] = sensorReading;

			// Wait until next minute before taking another reading if simulation is still ongoing
			while (minutesElapsed.get() == minute && !done.get()) {};
		}
	}

}
