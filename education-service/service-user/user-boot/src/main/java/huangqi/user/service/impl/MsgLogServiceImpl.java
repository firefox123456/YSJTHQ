package huangqi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import huangqi.user.entity.MsgLog;
import huangqi.user.entity.MsgLogStatus;
import huangqi.user.service.MsgLogService;
import huangqi.user.mapper.MsgLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author "黄骐"
* @description 针对表【msg_log(消息投递日志)】的数据库操作Service实现
* @createDate 2023-09-05 20:27:57
*/
@Service
public class MsgLogServiceImpl extends ServiceImpl<MsgLogMapper, MsgLog> implements MsgLogService{

    @Autowired
    MsgLogMapper msgLogMapper;


    @Override
    public void updateStatus(String msgId, MsgLogStatus deliverSuccess) {
        msgLogMapper.updateStatus(msgId,deliverSuccess.getCode());
    }

    @Override
    public void updateTryCount(String msgId, Date nextTryTime) {

    }
}




