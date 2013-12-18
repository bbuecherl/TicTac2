package tk.agarsia.tictac2.model.player.human;

import android.util.Log;

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
		this.interval = interval*1000;
		this.humanPlayer = humanPlayer;		
		start();
	}
	
	public void restart(){
		timePassed = 0;
		intervalOver = false;
		startTime = System.currentTimeMillis();
		
		Log.i("test", "" + interval);
		
		if(interval<=0)
			cancel();
	}
	
	public void cancel() {
		intervalOver = true;
	}
		
	/**
	 * checks startTime against currentTime until the time passed is bigger than the interval
	 */
	@Override
    public void run() {
    	while(!intervalOver){
    		timePassed = System.currentTimeMillis() - startTime;
    		if(timePassed >= interval){
    			humanPlayer.intervalPassed();
    			intervalOver = true;
    		}
    		
    		try {
    			Thread.sleep(10);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }	
}
