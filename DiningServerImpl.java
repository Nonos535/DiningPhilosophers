import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningServerImpl implements DiningServer 
{

	//Set number of Philosophers, States
	public static final int NUM_OF_PHILS = 5;
	enum State {Thinking, Eating, Hungry};
	//Initialize lock object, State and Condition arrays
	Lock lock;
	State[] state;
	Condition[] forks;
	
	DiningServerImpl() 
	{
		//create lock object for critical sections
		lock = new ReentrantLock();
		//Create state variable for each philosopher
		state = new State[NUM_OF_PHILS];
		//Create monitors for forks
		forks = new Condition[NUM_OF_PHILS];
		for (int i=0; i<NUM_OF_PHILS; i++) 
		{
			//initialize state to thinking
			//initialize forks to new condition variable
			state[i] = State.Thinking;
			forks[i] = lock.newCondition();
		}
	}

  public void takeForks(int pNumber)
  {
	  //lock the object for critical section
	  lock.lock();
	  try 
	  {
		  state[pNumber] = State.Hungry;
		  System.out.println("Philosopher "+pNumber+" is hungry.");
		  //if neither of the surrounding philosophers are eating
		  if (!(state[(pNumber - 1 + NUM_OF_PHILS) % NUM_OF_PHILS] 
				  == State.Eating) && 
				  !(state[(pNumber + 1) % NUM_OF_PHILS] 
						  == State.Eating)) 
		  {
			  //set state to eating
			  state[pNumber] = State.Eating;
		  //else if one or more of surrounding philosophers are eating
		  } 
		  else 
		  {
			  //wait till the forks are released, then eat
			  forks[pNumber].await();
			  state[pNumber] = State.Eating;
		  }
	  } 
	  catch (InterruptedException e) 
	  {
		  System.out.println("There's been an interruption!!!");
	  } 
	  finally 
	  {
		  //unlock after the critical section is completed
		  lock.unlock();
	  }
  }

  public void returnForks(int pNumber)
  {
	  //lock the object for critical section
	 lock.lock();
	 try 
	 {
		 System.out.println("Philosopher "+pNumber+ " is done eating.");
		 //done eating, release the forks (allow waiting people to eat)
		 forks[pNumber].signal();
		 //if else to deal with wrap around of philosopher 4
		 if (pNumber==4) {
			 forks[0].signal();
		 } 
		 else 
		 {
			 forks[pNumber + 1].signal();
		 }
	 } 
	 finally 
	 {
	 	  //unlock after the critical section is completed
		  lock.unlock();
	 }
  }
}
