package sampleloom;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestLoom {

	public static void main(String[] args) {
		try {
			Instant start = java.time.Instant.now();
			
			//Loom way of spawning threads
			try (var scope = FiberScope.open()) {
				start = java.time.Instant.now();
				for (int i = 1; i <= 100; i++) {
					scope.schedule(new SimpleTask());					
				}
				
			}
			System.out.println("Time taken by loom:: " + Duration.between(start, Instant.now()).toMillis());
			//traditional way of spawning threads
			try {
				start = java.time.Instant.now();
				ExecutorService executor = Executors.newWorkStealingPool(40);
				for (int i = 1; i <= 100; i++)
					executor.execute(new SimpleTask());

				executor.shutdown();
				executor.awaitTermination(1, TimeUnit.MINUTES);
				System.out.println(
						"Time taken by traditional threads:: " + Duration.between(start, Instant.now()).toMillis());
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class SimpleTask implements Runnable {
	static int counter = 0;

	@Override
	public void run() {
		try {
			Thread.sleep(100);
			System.out.println("Heelo World!!" + counter++);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
