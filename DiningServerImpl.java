import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningServerImpl implements DiningServer {

	public static final int NUM_OF_PHILS = 5;
	enum State {Thinking, Eating, Hungry};
	Lock lock;
	State[] state;
	Condition[] forks;
	
	DiningServerImpl() {
		lock = new ReentrantLock();
		state = new State[NUM_OF_PHILS];
		forks = new Condition[NUM_OF_PHILS];
		for (int i=0; i<NUM_OF_PHILS; i++) {
			state[i] = State.Thinking;
			forks[i] = lock.newCondition();
		}
	}

  public void takeForks(int pNumber){
	  lock.lock();
	  try {
		  state[pNumber] = State.Hungry;
		  System.out.println("Philosoper "+pNumber+" is hungry.");
		  if (!(state[(pNumber - 1 + NUM_OF_PHILS) % NUM_OF_PHILS] == State.Eating) && 
				  !(state[(pNumber + 1) % NUM_OF_PHILS] == State.Eating)) {
			  state[pNumber] = State.Eating;
		  } else {
			  forks[pNumber].await();
			  state[pNumber] = State.Eating;
		  }
	  } catch (InterruptedException e) {
		e.printStackTrace();
	} finally {
		  lock.unlock();
	  }
  }

  public void returnForks(int pNumber){
	 lock.lock();
	 try {
		  System.out.println("Philosopher "+pNumber+ " is done eating.");
		  forks[pNumber].signal();
		  if (pNumber==4) {
			  forks[0].signal();
		  } else {
			  forks[pNumber + 1].signal();
		  }
	 } finally {
			  lock.unlock();
		  }
  }
}
