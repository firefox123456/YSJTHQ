package huangqi.user.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import huangqi.user.entity.admin.User;
import huangqi.user.mapper.AdminLoginMapper;
import huangqi.user.service.AdminLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台管理登录接口
 *
 * @author "黄骐"
 * @date 2023/08/27 19:48
 **/
@Service
@Slf4j
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    AdminLoginMapper adminLoginMapper;

    @Override
    public User login(User user) {

        User correctUser = adminLoginMapper.queryByUsername(user.getUsername());
        if (!correctUser.getPassword().equals(user.getPassword())){
            log.info(user.toString()+" 登录失败！");
            return null;
        }

        //对密码进行数据脱敏
        String password = DesensitizedUtil.password(correctUser.getPassword());
        correctUser.setPassword(password);

        return correctUser;
    }
}
