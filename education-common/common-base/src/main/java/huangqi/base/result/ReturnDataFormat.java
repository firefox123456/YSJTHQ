package huangqi.base.result;

/**
 * 统一返回类
 *
 * @author "黄骐"
 * @date 2023/08/17 23:08
 **/

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ReturnDataFormat {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有
    private ReturnDataFormat() {}

    //静态方法
    public static  ReturnDataFormat ok() {
        ReturnDataFormat r = new ReturnDataFormat();
        r.setSuccess(true);
        r.setCode(520);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static ReturnDataFormat error() {
        ReturnDataFormat r = new ReturnDataFormat();
        r.setSuccess(false);
        r.setCode(250);
        r.setMessage("失败");
        return r;
    }

    public ReturnDataFormat success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ReturnDataFormat message(String message){
        this.setMessage(message);
        return this;
    }

    public ReturnDataFormat code(Integer code){
        this.setCode(code);
        return this;
    }

    public ReturnDataFormat data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ReturnDataFormat data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
