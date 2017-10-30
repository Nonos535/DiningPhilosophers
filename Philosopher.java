
public class Philosopher implements Runnable {
	
	DiningServer server;
	int number;
	
	public Philosopher(DiningServer server, int number) {
		this.server = server;
		this.number = number;
	}

	@Override
	public void run() {
		while(true) {
			think();
			eat();
		}
	}
	
	public void think() {
		System.out.println("Philosopher " +number+ " is thinking.");
		try {
		Thread.sleep(5000);
		} catch (InterruptedException e){}
	}
	
	public void eat() {
		server.takeForks(number);
		System.out.println("Philosopher " +number+ " is eating.");
		try {
		Thread.sleep(5000);
		} catch (InterruptedException e){}
		server.returnForks(number);
	}

}
