package com.atwk.service;

import com.atwk.bean.Employee;
import com.atwk.bean.EmployeeExample;
import com.atwk.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工CRUD业务逻辑层
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;



    /**
     * 查询所有员工信息
     * @return
     */
    public List<Employee> getAll(){
        List<Employee> employees = employeeMapper.selectByExampleWithDept(null);
        return employees;
    }

    /**
     * 员工保存
     *
     * @param employee
     * @return void
     */
    public void saveEmp(Employee employee) {

        employeeMapper.insertSelective(employee);
    }

    /**
     * @Description 检验员工姓名是否可用
     *
     * @param empName 员工姓名
     * @return true：可用 ，false：不可用
     */
    public boolean checkUser(String empName) {

        //创建查询条件
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);

        long count = employeeMapper.countByExample(example);
        return count==0;
    }

    /**
     * @Description 查询员工信息
     *
     * @param id 员工ld
     * @return Employee 员工信息
     */
    public Employee getEmp(Integer id) {

        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * @Description 修改员工信息
     *
     * @param employee 员工信息
     */
    public void updateEmp(Employee employee) {

        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 删除员工
     *
     * @param id
     */
    public void delEmp(Integer id) {

        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除员工
     *
     * @param ids 批量删除id
     */
    public void delEmpBatch(List<Integer> ids) {

        //按条件删除
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);

        employeeMapper.deleteByExample(example);
    }
}
