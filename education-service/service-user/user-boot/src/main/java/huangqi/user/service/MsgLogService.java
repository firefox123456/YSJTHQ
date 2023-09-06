package huangqi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import huangqi.user.entity.MsgLog;
import huangqi.user.entity.MsgLogStatus;

/**
* @author "黄骐"
* @description 针对表【msg_log(消息投递日志)】的数据库操作Service
* @createDate 2023-09-05 20:27:57
*/
public interface MsgLogService extends IService<MsgLog> {
    void updateStatus(String msgId, MsgLogStatus deliverSuccess);
}
