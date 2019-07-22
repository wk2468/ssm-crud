package com.atwk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atwk.bean.Department;
import com.atwk.dao.DepartmentMapper;
import java.util.List;

/**
 * @ClassName DepartmentService
 * @Description 部门信息服务层
 * @Author wk
 * @Date 2019/6/1 21:33
 * @Version 1.0
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * @Description 查询所有部门信息
     *
     * @param
     * @return java.util.List<com.atwk.bean.Department>
     */
    public List<Department> getDepts(){

        return departmentMapper.selectByExample(null);
    }
}
