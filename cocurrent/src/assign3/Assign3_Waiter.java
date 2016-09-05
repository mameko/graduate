package assign3;

public class Assign3_Waiter {
	int num;
	boolean pEating[];
	boolean pLeft[];
	boolean pRight[];
	
	public Assign3_Waiter(int Num) {
		// TODO Auto-generated constructor stub
		num = Num;	
		pEating = new boolean[num];
		pLeft = new boolean[num];
		pRight = new boolean[num];
		
		for (int i = 0; i < num; i++) {
			pLeft[i] = true;
			pRight[i] = true;
		}
		
	}
	
	//Every time the waiter receives a request he/she will make sure that the chopsticks must be used by other philosophers 
	//before the same philosopher pick it up again. Every time the philosopher eats, he/she sets the flag of his/her 
	//own chopsticks(left and right) to false. Then when he/she finished eating, he/she sets the flags of the philosopher 
	//beside he/she true(the left chopstick of the philosopher on his/her right hand side and the right chopstick of the philosopher 
	//on his/her left hand side). 
	//So no starving.
	//
	//Because every time before eating he/she will check whether the neighbour philosopher is eating, if either of
	//them is eating, this philosopher will go to the waiting queue. So he/she will not just pick up one chopstick and lead
	//to deadlock.
	//So no deadlock.	
	
	// {inv. pEating[seat] = true /\ pEating[(seat - 1)%num]=false /\ pEating[(seat + 1)%num]=false} 
	synchronized void request(int seat){
		int d = seat==0 ? num-1 : seat - 1;
		while(pEating[ (seat + 1)%num ]|| pEating[d]||pLeft[seat] == false ||pRight[seat] == false)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		pEating[seat] = true;
		System.out.println("philosopher "+seat+" eating");
		pLeft[seat] = false;
		pRight[seat] = false;	
	}
	
	synchronized void done(int seat){
		pEating[seat] = false;
		int d = seat==0 ? num-1 : seat - 1;
		pLeft[d] = true;
		pRight[(seat + 1)%num] = true;
		System.out.println("philosopher "+seat+" finished eating");
		notifyAll();
	}
}