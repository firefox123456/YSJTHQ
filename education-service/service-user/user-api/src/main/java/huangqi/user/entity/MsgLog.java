package huangqi.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息投递日志
 * @TableName msg_log
 */
@TableName(value ="msg_log")
@Data
public class MsgLog implements Serializable {
    public MsgLog(String msgId, String msg, String exchange, String routingKey) {
        this.msgId = msgId;
        this.msg = msg;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    /**
     * 消息唯一标识
     */
    @TableId
    private String msgId;

    /**
     * 消息体, json格式化
     */
    private String msg;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 状态: 0投递中 1投递成功 2投递失败 3已消费
     */
    private Integer status;

    /**
     * 重试次数
     */
    private Integer tryCount;

    /**
     * 下一次重试时间
     */
    private Date nextTryTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}