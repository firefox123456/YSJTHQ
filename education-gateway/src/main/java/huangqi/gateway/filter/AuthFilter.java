package huangqi.gateway.filter;

/**
 * 用户认证过滤器
 *
 * @author "黄骐"
 * @date 2023/08/31 09:53
 **/

import com.alibaba.fastjson.JSON;
import huangqi.base.result.ResultCode;
import huangqi.base.result.ReturnDataFormat;
import huangqi.gateway.utils.JwtUtils;
import huangqi.redis.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 *
 * @author ruoyi
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered
{
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
//    @Autowired
//    private IgnoreWhiteProperties ignoreWhite;

    @Autowired
    private RedisUtils redisService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (url.equals("/admin/login")||url.equals("/user/login"))
        {
            return chain.filter(exchange);
        }

        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //token为空
        if (StringUtils.isEmpty(userId))
        {
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE,  MediaType.APPLICATION_JSON_VALUE);
            ReturnDataFormat result=ReturnDataFormat.ok().code(ResultCode.NOLOGIN.getCode()).data("message",ResultCode.NOLOGIN.getMessage());
            DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }
        //token续命
        if ((redisService.hasKey("admin-login:"+userId)||redisService.hasKey("user-login:"+userId))&&(redisService.getExpire("admin-login:"+userId)<60*30||redisService.getExpire("user-login:"+userId)<60*30))
        {
            if (redisService.hasKey("admin-login:"+userId)) {
                redisService.expire("admin-login:"+userId,3600*24);
            }else if (redisService.hasKey("admin-login:"+userId)){
                redisService.expire("user-login:"+userId,3600*24);
            }
        }
        //token失效返回
        else{
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE,  MediaType.APPLICATION_JSON_VALUE);
            ReturnDataFormat result=ReturnDataFormat.ok().code(ResultCode.LOGINOVER.getCode()).data("message",ResultCode.LOGINOVER.getMessage());
            DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder()
    {
        return -200;
    }
}
