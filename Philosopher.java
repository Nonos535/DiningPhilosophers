
public class Philosopher implements Runnable 
{
	
	DiningServer server;
	int number;
	
	//Basic constructor for Philosopher class
	public Philosopher(DiningServer server, int number) 
	{
		this.server = server;
		this.number = number;
	}

	@Override
	public void run() 
	{
		//Philosopher will continue to think and eat forever
		while(true) 
		{
			think();
			eat();
		}
	}
	
	public void think() 
	{
		System.out.println("Philosopher " +number+ " is thinking.");
		try 
		{
		//Think for 10 milliseconds
		Thread.sleep(10000);
		} 
		catch (InterruptedException e)
		{
			System.out.println("There's been an interruption!!!");
		}
	}
	
	public void eat() 
	{
		//Must take forks before you can eat!
		server.takeForks(number);
		System.out.println("Philosopher " +number+ " is eating.");
		try 
		{
	    //Spend 5 milliseconds eating
		Thread.sleep(5000);
		} 
		catch (InterruptedException e)
		{
			System.out.println("There's been an interruption!!!");
		}
		//Return the forks
		server.returnForks(number);
	}

}
