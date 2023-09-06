package huangqi.user.consumer;

import com.rabbitmq.client.Channel;
import huangqi.user.config.RabbitMQConfig;
import huangqi.user.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Mail消费者
 *
 * @author "黄骐"
 * @date 2023/09/05 21:26
 **/
@Component
@Slf4j
public class MailConsumer {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel)  {
        log.info(String.valueOf(message));
        log.info(message.toString());
        log.info(new String(message.getBody()));
//        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
//        if (null == msgLog || msgLog.getStatus().equals(MsgLogStatus.CONSUMED_SUCCESS)) {// 消费幂等性
//            log.info("重复消费, msgId: {}", msgId);
//            return;
//        }

//        MessageProperties properties = message.getMessageProperties();
//        long tag = properties.getDeliveryTag();
//
//        boolean success = mailUtil.send(mail);
//        if (success) {
//            msgLogService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
//            channel.basicAck(tag, false);// 消费确认
//        } else {
//            channel.basicNack(tag, false, true);
//        }
    }

}
