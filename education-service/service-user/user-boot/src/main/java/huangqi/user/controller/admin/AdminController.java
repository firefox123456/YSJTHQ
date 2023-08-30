package huangqi.user.controller.admin;

import huangqi.base.exception.LoginException;
import huangqi.base.result.ReturnDataFormat;
import huangqi.redis.utils.RedisUtils;
import huangqi.user.entity.admin.User;
import huangqi.user.service.AdminLoginService;
import huangqi.web.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
//    @PostMapping("test")
//    public User test(@RequestBody User user){
//        User login = adminLoginService.login(user);
//        return login;
//    }

    @PostMapping("/login")
    @ApiOperation("admin登录")
    public ReturnDataFormat login(@RequestBody User user, HttpServletRequest request) throws LoginException {
        User login = adminLoginService.login(user);
        log.info("登录成功"+login.toString());
        //生成token
        String token = JwtUtils.getJwtToken(login.getId(), login.getNickName());
        //redis token设置有效期为一天
        redisUtils.set("admin-login:"+login.getId(),token,3600*24);
        return ReturnDataFormat.ok().data("token",token).data("userInfo",login);
    }

    @PostMapping("/logout")
    @ApiOperation("登录退出")
    public ReturnDataFormat logout(@RequestBody User user){
        redisUtils.del("admin-login:"+user.getId());
        return ReturnDataFormat.ok();
    }

}
