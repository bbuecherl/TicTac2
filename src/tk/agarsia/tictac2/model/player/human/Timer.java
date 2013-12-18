package tk.agarsia.tictac2.model.player.human;

public class Timer extends Thread{

	private long startTime;
	private long timePassed = 0;
	private int interval;
	private HumanLocal humanPlayer;
	private boolean intervalOver = false;
	
	/**
	 * constructor for Timer
	 * @param humanPlayer needs HumanLocal to call back that the time is up
	 * @param interval in s
	 */
	public Timer(HumanLocal humanPlayer, int interval){	
		this.interval = interval;
		this.humanPlayer = humanPlayer;		
		startTime = System.nanoTime();
		start();
	}
		
	/**
	 * checks startTime against currentTime until the time passed is bigger than the interval
	 */
	@Override
    public void run() {
    	while(!intervalOver){
    		timePassed = (System.nanoTime() - startTime) / 1000000;
    		if(timePassed >= (interval * 1000)){
    			humanPlayer.intervalPassed();
    			intervalOver = true;
    		}
    	}
    }	
}
