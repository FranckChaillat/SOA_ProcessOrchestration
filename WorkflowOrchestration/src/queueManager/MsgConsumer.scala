package queueManager

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.QueueingConsumer
import processOrchestration.ProcessManager

/**
 * @author fra
 */
class MsgConsumer {
  
 
   private final val EXCHANGE_NAME = "direct"
   private val factory = new ConnectionFactory
   factory.setHost("localhost")
   private val connection = factory.newConnection
   private val channel = connection.createChannel
  
   def rcv(way:String){

        channel.exchangeDeclare(EXCHANGE_NAME, "direct")
        val queueName = channel.queueDeclare().getQueue
        channel.queueBind(queueName, EXCHANGE_NAME, "courseEdition")
        
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C")
        
        val consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
          
            val delivery = consumer.nextDelivery
            val message = new String(delivery.getBody)
            val routingKey = delivery.getEnvelope().getRoutingKey
            
            new Thread(new Runnable{
              
               var res = new Message(routingKey, message)
               def run() {
                 res = new ProcessManager(res).run
                 }}).start()
            
            
          
            System.out.println(" [x] WO Receives '" + routingKey + "':'" + message + "'")
        }
   }
  
}