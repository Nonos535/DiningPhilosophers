
public class DiningPhilosophersDriver {
	public static void main(String[] args) {
		DiningServer server = new DiningServerImpl();
		
		//initialize an array of 5 philosophers
		Philosopher[] pArray = new Philosopher[DiningServerImpl.NUM_OF_PHILS];
	     
	    // create a thread for each philosopher
	    for (int i = 0; i < DiningServerImpl.NUM_OF_PHILS; i++)
	         pArray[i] = new Philosopher(server,i);
	     
	     //start the philosophers threads
	     for (int i = 0; i < DiningServerImpl.NUM_OF_PHILS; i++)
	         new Thread(pArray[i]).start();
	}

}
