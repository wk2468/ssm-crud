package com.atwk.test;

import com.atwk.bean.Department;
import com.atwk.bean.DepartmentExample;
import com.atwk.bean.Employee;
import com.atwk.dao.DepartmentMapper;
import com.atwk.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 * 测试dao层工作
 * <p>
 * 推荐spring项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 * 1.导入spring-test模块
 * 2.@ContextConfiguration("classpath: ")指定Spring配置文件的位置
 * <p>
 * Created by wk on 2019/4/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private DepartmentExample departmentExample;

    /**
     * 测试添加部门
     */
    @Test
    public void testInsertDept() {

        //插入1个部门
        departmentMapper.insertSelective(new Department(null, "研发部"));
        departmentMapper.insertSelective(new Department(null, "测试部"));
        departmentMapper.insertSelective(new Department(null, "运维部"));
        departmentMapper.insertSelective(new Department(null, "人事部"));
    }

    /**
     * @Description 测试查询部门信息
     *
     * @param
     * @return void
     */
    @Test
    public void testSelectDept() {
        Department department = departmentMapper.selectByPrimaryKey(1);
        System.out.println(department);
        List<Department> departments = departmentMapper.selectByExample(departmentExample);
        System.out.println(departments);
    }

    /**
     * 测试修改部门信息
     */
    @Test
    public void testUpdateDept() {

        int i = departmentMapper.updateByPrimaryKey(new Department(4, "666部"));
        System.out.println("更新" + i + "条信息！");
    }

    /**
     * 测试删除部门信息
     */
    @Test
    public void testDeleteDept() {
        departmentMapper.deleteByPrimaryKey(4);
        System.out.println("删除成功！");
    }


    /**
     * 测试添加员工信息
     */
    @Test
    public void testInsertEmp() {
        //单条插入
//        employeeMapper.insertSelective(new Employee(null,"zhangsan","M","zhangsan@guigu.com",1));
        //批量插入
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            //UUID生成随机员工姓名
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null, uid, "F", uid + "@guigu.com", 2));
        }
        System.out.println("批量完成！");
    }

    /**
     * 测试查询员工信息
     */
    @Test
    public void testSelectEmp() {

        Employee employee = employeeMapper.selectByPrimaryKeyWithDept(1);
        System.out.println(employee);
    }

}
