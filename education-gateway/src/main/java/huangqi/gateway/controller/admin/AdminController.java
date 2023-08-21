package huangqi.gateway.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员登陆接口
 *
 * @author "黄骐"
 * @date 2023/08/20 15:56
 **/
@RestController
public class AdminController {

    @RequestMapping("test")
    String test(){
        return "huangqi";
    }
}
