package huangqi.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import huangqi.teacher.entity.EduTeacher;
import huangqi.teacher.mapper.EduTeacherMapper;
import huangqi.teacher.service.IEduTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 讲师接口实现类
 *
 * @author "黄骐"
 * @date 2023/08/19 23:40
 **/
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> page1) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(page1, wrapper);
        List<EduTeacher> records = page1.getRecords();
        long current = page1.getCurrent();
        long size = page1.getSize();
        long pages = page1.getPages();
        long total = page1.getTotal();

        boolean next = page1.hasNext();
        boolean previous = page1.hasPrevious();
        //把分页数据获取出来放到Map集合中
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("total",total);
        map.put("hasNext",next);
        map.put("hasPrevious",previous);

        return map;
    }
}
