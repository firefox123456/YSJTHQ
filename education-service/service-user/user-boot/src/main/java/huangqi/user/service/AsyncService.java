package huangqi.user.service;

public interface AsyncService {
    //发送邮件
    void sendLoginMail(String to, String ip);

}
