package huangqi.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * jwt工具类
 *
 * @author "黄骐"
 * @date 2023/08/30 14:44
 **/
public class JwtUtils {

    //两个常量
    //秘钥
    public static final String APP_SECRET = "HuangQi";

    //生成token字符串的方法
    public static String getJwtToken(String id, String nickname){
        //String id, String nickname
        //用户id      用户名称

        String JwtToken = Jwts.builder()
                //设置jwt的头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置token的过期时间  null设置token过期时间用不过期
                .setSubject("token")
                .setIssuedAt(new Date())
                .setExpiration(null)
                //设置token的主体部分（存储用户信息的部分）
                .claim("id", id)
                .claim("nickname", nickname)

                //签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    //验证token
    public static boolean checkToken(String jwtToken) {
        if(!StringUtils.hasLength(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    //验证token
//    public static boolean checkToken(HttpServletRequest request) {
//        try {
//            //通过request把消息头得到再来判断token
//            String jwtToken = request.getHeader("token");
//            if(!StringUtils.hasLength(jwtToken)) {
//                return false;
//            }
//            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("token");
        if(!StringUtils.hasLength(token)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}

