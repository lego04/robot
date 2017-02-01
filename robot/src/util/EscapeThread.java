package util;

import lejos.hardware.Button;

public class EscapeThread implements Runnable {
	
	private Thread t;

	public EscapeThread() {
		t = null;
	}
	
	
	
	public void startThread() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		} else {
			interruptThread();
			t = new Thread(this);
			t.start();
		}
	}
	
	public void interruptThread() {
		if (!t.isInterrupted()) {
			t.interrupt();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}
	}

		
		

}
