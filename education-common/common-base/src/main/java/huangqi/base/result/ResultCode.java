package huangqi.base.result;

import lombok.Getter;

@Getter
public enum ResultCode {
    //参数异常
    IllegalArgument(600,"参数异常"),
    //没有权限抛出异常
    QXBZ(700,"没有访问权限"),
    //用户名或者密码为空
    LOGINFAIL(800,"登录失败");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
