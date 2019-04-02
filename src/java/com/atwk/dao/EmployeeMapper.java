package com.atwk.dao;

import com.atwk.bean.Employee;
import com.atwk.bean.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer empId);

    /**
     * 按条件查询员工信息（带部门信息）
     * @param example
     * @return
     */
    List<Employee> selectByExampleWithDept(EmployeeExample example);

    /**
     * 按主键查询员工信息（带部门信息）
     * @param empId
     * @return
     */
    Employee selectByPrimaryKeyWithDept(Integer empId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}