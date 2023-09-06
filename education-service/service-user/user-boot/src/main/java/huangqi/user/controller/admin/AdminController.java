package huangqi.user.controller.admin;

import huangqi.base.exception.LoginException;
import huangqi.base.result.ReturnDataFormat;
import huangqi.redis.utils.RedisUtils;
import huangqi.user.config.RabbitMQConfig;
import huangqi.user.entity.MsgLog;
import huangqi.user.entity.admin.User;
import huangqi.user.service.AdminLoginService;
import huangqi.user.service.AsyncService;
import huangqi.user.service.MsgLogService;
import huangqi.web.utils.IdWorker;
import huangqi.web.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理用户登陆接口
 *
 * @author "黄骐"
 * @date 2023/08/21 19:56
 **/
@RequestMapping("admin")
@RestController
@Api("后台管理用户模块")
@Slf4j
public class AdminController {

    @Autowired
    AdminLoginService adminLoginService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AsyncService asyncService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MsgLogService msgLogService;

    @GetMapping("test")
    public String test() {

        return "good";
    }

    @PostMapping("/login")
    @ApiOperation("admin登录")
    public ReturnDataFormat login(@RequestBody User user, HttpServletRequest request) throws LoginException {
        User login = adminLoginService.login(user);
        log.info("登录成功" + login.toString());
        //异步线程池发送邮件登陆
        asyncService.sendLoginMail(login.getMail(), request.getRemoteHost());
        //生成token
        String token = JwtUtils.getJwtToken(login.getId(), login.getUsername());
        //redis token设置有效期为一天
        redisUtils.set("admin-login:" + login.getId(), token, 3600 * 24);
        return ReturnDataFormat.ok().data("token", token).data("userInfo", login);
    }

    @GetMapping("/logout")
    @ApiOperation("登录退出")
    public ReturnDataFormat logout(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //使用rabbitMQ
        Long msgId = new IdWorker().nextId();
        MsgLog msgLog = new MsgLog(msgId.toString(), id, RabbitMQConfig.MAIL_EXCHANGE_NAME, RabbitMQConfig.MAIL_ROUTING_KEY_NAME);
        msgLogService.save(msgLog);

        CorrelationData correlationData = new CorrelationData(msgId.toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.MAIL_EXCHANGE_NAME, RabbitMQConfig.MAIL_ROUTING_KEY_NAME,id, correlationData);// 发送消息

        //未使用rabbitMQ;
//        if (redisUtils.hasKey("admin-login:"+id)) {
//              redisUtils.del("admin-login:"+id);
//        }
        return ReturnDataFormat.ok();
    }

}
