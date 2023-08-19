package huangqi.teacher.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前台讲师查询条件实体类
 *
 * @author "黄骐"
 * @date 2023/08/19 23:49
 **/
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}

