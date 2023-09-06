package huangqi.user.service;


import huangqi.base.exception.LoginException;
import huangqi.base.result.ReturnDataFormat;
import huangqi.user.entity.admin.User;

public interface AdminLoginService {

    User login( User user) throws LoginException;

    User queryUserByID(String id);
}
