package com.atwk.test;

import com.atwk.bean.Department;
import com.atwk.bean.Employee;
import com.atwk.dao.DepartmentMapper;
import com.atwk.dao.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试dao层工作
 *
 * 推荐spring项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 * 1.导入spring-test模块
 * 2.@ContextConfiguration("classpath: ")指定Spring配置文件的位置
 *
 * Created by wk on 2019/4/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 测试departmentMapper
     */
    @Test
    public void testCRUD(){

        //插入1个部门
        departmentMapper.insertSelective(new Department(null,"研发部"));
        departmentMapper.insertSelective(new Department(null,"测试部"));
        departmentMapper.insertSelective(new Department(null,"运维部"));
        departmentMapper.insertSelective(new Department(null,"人事部"));
    }

}
