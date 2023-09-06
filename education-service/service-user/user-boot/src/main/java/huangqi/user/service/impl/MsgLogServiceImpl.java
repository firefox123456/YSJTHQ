package huangqi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import huangqi.user.entity.MsgLog;
import huangqi.user.entity.MsgLogStatus;
import huangqi.user.service.MsgLogService;
import huangqi.user.mapper.MsgLogMapper;
import org.springframework.stereotype.Service;

/**
* @author "黄骐"
* @description 针对表【msg_log(消息投递日志)】的数据库操作Service实现
* @createDate 2023-09-05 20:27:57
*/
@Service
public class MsgLogServiceImpl extends ServiceImpl<MsgLogMapper, MsgLog> implements MsgLogService{

    @Override
    public void updateStatus(String msgId, MsgLogStatus deliverSuccess) {

    }
}




