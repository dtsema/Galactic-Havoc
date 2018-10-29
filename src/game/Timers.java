package game;

public class Timers{
	public int countDown;
	boolean countDone = false;
	
	public Timers(int countDown){
		this.countDown = countDown;
	}
		
	public boolean cD(){
		countDone = false;
		
		if (countDown> 0){
			this.countDown--;
		}
		else if (countDown == 0){
			countDone = true;
		}
		else if (countDown == -1){
			countDone = false;
		}
	return countDone;
	}
	
	public void setCount(int countDown){
		this.countDown=countDown;
	}
	
	public int getCount(){
		return countDown;
	}
}