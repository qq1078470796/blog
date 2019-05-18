package hao.blog.www.activemq;

import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class SmsAuthenCode implements MessageListener {
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        // 调用SMS服务发送短信

        try {
            // 发送短信 Map 来自ActiveMQ 生成者
            SendMessage.sendMessages( mapMessage.getString("code"), mapMessage.getString("telephone") );
            System.out.println( "-----发送消息成功..."+mapMessage.getString("code"));
        } catch (Exception e) {//JMS
            e.printStackTrace();
        }
    }
}
