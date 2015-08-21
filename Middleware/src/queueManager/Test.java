package queueManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Test {

	public static void main(String[] args) {
		
		
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				MsgTransmitter mt = new MsgTransmitter();
				mt.send("coucou", "courseEdition", "rep-courseEdition");
			}
		}).start();
	
	}

}
