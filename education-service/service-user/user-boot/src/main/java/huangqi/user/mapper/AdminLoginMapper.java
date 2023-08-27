package huangqi.user.mapper;


import huangqi.user.entity.admin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminLoginMapper {

    User queryByUsername(@Param("username") String username);

}
