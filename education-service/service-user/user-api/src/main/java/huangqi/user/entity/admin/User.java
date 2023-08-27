package huangqi.user.entity.admin;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 用户表
* @TableName acl_user
*/
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 会员id
    */
    @ApiModelProperty("会员id")
    private String id;
    /**
    * 微信openid
    */
    @ApiModelProperty("微信openid")
    private String username;
    /**
    * 密码
    */
    @ApiModelProperty("密码")
    private String password;
    /**
    * 昵称
    */
    @ApiModelProperty("昵称")
    private String nickName;
    /**
    * 用户头像
    */
    @ApiModelProperty("用户头像")
    private String salt;
    /**
    * 用户签名
    */
    @ApiModelProperty("用户签名")
    private String token;
    /**
    * 逻辑删除 1（true）已删除， 0（false）未删除
    */
    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date gmtModified;

}
