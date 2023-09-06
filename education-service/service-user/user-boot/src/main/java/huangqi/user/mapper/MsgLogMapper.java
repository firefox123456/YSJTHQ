package huangqi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import huangqi.user.entity.MsgLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author "黄骐"
* @description 针对表【msg_log(消息投递日志)】的数据库操作Mapper
* @createDate 2023-09-05 20:27:57
* @Entity huangqi.user.admin.MsgLog
*/
@Mapper
public interface MsgLogMapper extends BaseMapper<MsgLog> {
    void updateStatus(@Param("msgId") String msgId, @Param("code")Integer code) ;
}




