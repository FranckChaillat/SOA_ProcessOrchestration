package queueManager;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MsgTransmitter {
	
	private static final String EXCHANGE_NAME = "direct";

	
	public void send(String msg, String way, String replyQueue){
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
       

        try {
        	 Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
			 channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		     
			 for(int i=0; i<1; i++){
				 channel.basicPublish(EXCHANGE_NAME, way, null, (msg+i).getBytes());
				 System.out.println(" [x] Sent '" + way + "':'" + msg +i+ "'");
				 Thread.sleep(100000);
			 }

		     channel.close();
		     connection.close();
		     
		} catch (IOException | TimeoutException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
	}
}
