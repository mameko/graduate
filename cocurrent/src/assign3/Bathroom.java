package assign3;

public class Bathroom {
	int capacity;
	Sex turn = Sex.Female;
	int maleWaiting = 0;
	int femaleWaiting = 0;
	int maleInside = 0;
	int femaleInside = 0;
	
	public Bathroom(int num){
		capacity = num;
	}
	
	//{inv. (maleInside == 0 && femaleInside != 0)||(femaleInside == 0 && maleInside != 0)&& (maleInside + femaleInsied <= capacity)}
	//
	synchronized void enter(int id, Sex s){
		System.out.println(id +"  "+s +" turn = "+ turn+" femaleIn = "+femaleInside + " maleIn = "+maleInside);
		if (s.equals(Sex.Female)) {
			femaleWaiting += 1;	
			while (femaleInside == capacity || (!turn.equals(Sex.Female)&& maleWaiting != 0)|| maleInside !=0){
				try {	
					System.out.println(id +"  "+s +" blocked !");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			femaleInside += 1;
			System.out.println("======================= "+id +"  "+s +"inside, turn = "+ turn+" femaleIn = "+femaleInside + " maleIn = "+maleInside);
			femaleWaiting -= 1;
		}else {
			maleWaiting += 1;
			while (maleInside == capacity || (!turn.equals(Sex.Male)&& femaleWaiting != 0)|| femaleInside !=0){
				try {	
					System.out.println(id +" "+s +" blocked !");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			maleInside += 1;
			System.out.println("======================= " +id +"  "+s +"inside, turn = "+ turn+" femaleIn = "+femaleInside + " maleIn = "+maleInside);
			maleWaiting -= 1;
		}
	}
	
	synchronized void leave(Sex s){
		if (s == Sex.Female) {
			femaleInside -=1;
			turn = Sex.Male;
		}else {
			maleInside -=1;
			turn = Sex.Female;
		}
		notifyAll();
	}
}
