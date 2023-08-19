package huangqi.teacher.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import huangqi.base.result.ReturnDataFormat;
import huangqi.teacher.entity.EduTeacher;
import huangqi.teacher.entity.query.TeacherQuery;
import huangqi.teacher.service.IEduTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 讲师路由
 *
 * @author "黄骐"
 * @date 2023/08/19 21:04
 **/
@RestController
@RequestMapping("teacher")
@Slf4j
public class TeacherController {

    @GetMapping("test")
    String test(){
        return "hello word";
    }
    @Resource
    private IEduTeacherService iEduTeacherService;

    @GetMapping("findAll")
    public ReturnDataFormat findAllTeacher() {
        List<EduTeacher> list = iEduTeacherService.list(null);
        return ReturnDataFormat.ok().data("list", list);
    }

    @DeleteMapping("delete/{id}")
    public ReturnDataFormat removeTeacher(@PathVariable String id) {
        boolean b = iEduTeacherService.removeById(id);
        return ReturnDataFormat.ok();
    }

    /**
     * 创建page对象
     * 使用mpSerbice中的page方法
     * 返回page的值
     */
    @GetMapping("{current}/{size}")
    ReturnDataFormat pageTeacher(@PathVariable Integer current,
                                 @PathVariable Integer size) {

        Page<EduTeacher> page = new Page<>(current, size);

        iEduTeacherService.page(page, null);

        return ReturnDataFormat.ok().data(new HashMap() {
            {
                put("total", page.getTotal());
                put("rows", page.getRecords());
            }
        });
    }

    /**
     * 条件查询+分页操作
     * QueryWrapper+Page对象
     * page对象
     * QueryWrapper对象
     */
    @PostMapping("condition/{current}/{size}")
    ReturnDataFormat confitionPageTeacher(@PathVariable Integer current,
                                          @PathVariable Integer size,
                                          @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> teacherPage = new Page<>(current, size);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

//        log.info(String.valueOf(teacherQuery==null));
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
//            log.info(String.valueOf(level));
        //判断条件值是否为空，如果不为空拼接条件
        if (StringUtils.hasLength(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (level !=null&&(level==1||level==2)) {
            wrapper.eq("level", level);
        }
        if (StringUtils.hasLength(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (StringUtils.hasLength(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        iEduTeacherService.page(teacherPage, wrapper);

        long total = teacherPage.getTotal();//总记录数
        List<EduTeacher> records = teacherPage.getRecords(); //数据list集合

        return ReturnDataFormat.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加讲师
     */
    @PostMapping("/addTeacher")
    public ReturnDataFormat addTeacher(@RequestBody EduTeacher eduTeacher) {
//        log.info(String.valueOf(eduTeacher));
        boolean save = iEduTeacherService.save(eduTeacher);
        return save ? ReturnDataFormat.ok() : ReturnDataFormat.error();
    }

    /**
     * 根据id查询讲师
     */
    @GetMapping("queryTeacherById/{id}")
    public ReturnDataFormat queryTeacherById(@PathVariable String id) {
        EduTeacher byId = iEduTeacherService.getById(id);
        return ReturnDataFormat.ok().data("teacher", byId);
    }

    /**
     * 讲师修改功能
     */
    @PostMapping("updateTeacher")
    public ReturnDataFormat updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = iEduTeacherService.updateById(eduTeacher);
        return b ? ReturnDataFormat.ok() : ReturnDataFormat.error();
    }
}
