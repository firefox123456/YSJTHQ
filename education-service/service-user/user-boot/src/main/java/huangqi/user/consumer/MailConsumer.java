package huangqi.user.consumer;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import huangqi.redis.utils.RedisUtils;
import huangqi.user.config.RabbitMQConfig;
import huangqi.user.entity.MsgLog;
import huangqi.user.entity.MsgLogStatus;
import huangqi.user.entity.admin.User;
import huangqi.user.service.AdminLoginService;
import huangqi.user.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * Mail消费者
 *
 * @author "黄骐"
 * @date 2023/09/05 21:26
 **/
@Component
@Slf4j
public class MailConsumer {

    @Value("${spring.mail.username}")
    String from;

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private RedisUtils redisUtils;

    @RabbitListener(queues = RabbitMQConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) {
        /**
         * 一种办法,害人不浅
         */
//        String id = new String(message.getBody());
        /**
         * 第二种办法，very good。
         */
        String o = JSONObject.parseObject(message.getBody(), String.class);
        /**
         * 获得唯一标志
         */
        String msgId = message.getMessageProperties().getHeader("spring_returned_message_correlation");

        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;

        log.info( "---------" + msgId + "-----------" + o+"-------"+tag);
        QueryWrapper<MsgLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("msg_id", msgId);
        MsgLog msgLog = msgLogService.list(queryWrapper).get(0);
        log.info("------------"+msgLog.toString());
        if (null == msgLog || msgLog.getStatus().equals(MsgLogStatus.CONSUMED_SUCCESS)) {// 消费幂等性
            log.info("重复消费, msgId: {}", msgId);
            try {
                channel.basicAck(tag, false);// 消费确认
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (redisUtils.hasKey("admin-login:" + o)) {
            redisUtils.del("admin-login:" + o);
        }
        User user = adminLoginService.queryUserByID(o);
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(user.getMail());
            StringBuilder sb = new StringBuilder();
            mimeMessageHelper.setSubject("退出提醒");
            sb.append("您的账号退出成功 " + "!");
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMailMessage);
            msgLogService.updateStatus(msgId, MsgLogStatus.CONSUMED_SUCCESS);
            channel.basicAck(tag, false);// 消费确认
        } catch (Exception e) {
            log.info("邮件发送失败" + "sendLoginMail");
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {

            }
        }

    }

}
