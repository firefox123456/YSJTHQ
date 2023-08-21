package huangqi.user.controller.admin;

import huangqi.base.result.ReturnDataFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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


    @PostMapping("/login")
    @ApiOperation("admin登录")
    public ReturnDataFormat login(){
        log.info("登录成功");
        return ReturnDataFormat.ok().data("token","admin");
    }

    @GetMapping("/info")
    @ApiOperation("用户信息")
    public ReturnDataFormat info() {
        return ReturnDataFormat.ok().data(new HashMap(){
            {
                put("roles","[admin]");
                put("name","admin");
                put("avatar","https://img2.baidu.com/it/u=385604385,3425222613&fm=253&fmt=auto&app=138&f=GIF?w=600&h=467");
            }
        });
    }

    @PostMapping("/logout")
    @ApiOperation("登录退出")
    public ReturnDataFormat logout(){
        return ReturnDataFormat.ok();
    }

}
