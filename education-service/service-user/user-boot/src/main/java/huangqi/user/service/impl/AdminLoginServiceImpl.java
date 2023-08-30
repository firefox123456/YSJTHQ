package huangqi.user.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import huangqi.base.exception.LoginException;
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
    public User login(User user) throws LoginException {
        User correctUser=null;
            //密码或者用户名为空
            if (user.getUsername() == null || user.getPassword() == null) {
                throw new LoginException("用户名或者密码为空！");
            }

            //查询不到数据
            correctUser = adminLoginMapper.queryByUsername(user.getUsername());
            if (correctUser == null) {
                throw new LoginException("用户名错误，或者人员未注册");
            }

            //密码错误
            if (!correctUser.getPassword().equals(user.getPassword())) {
                throw new LoginException("用户密码错误");
            }

            //正确返回 并对密码等数据进行数据脱敏
            String password = DesensitizedUtil.password(correctUser.getPassword());
            correctUser.setPassword(password);

        return correctUser;
    }
}
