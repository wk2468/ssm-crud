package com.atwk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atwk.bean.Department;
import com.atwk.bean.ResultMsg;
import com.atwk.service.DepartmentService;
import java.util.List;

/**
 * @ClassName DepartmentController
 * @Description 部门信息控制层
 * @Author wk
 * @Date 2019/6/1 21:31
 * @Version 1.0
 */
@RequestMapping("/department")
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * @Description 获取部门信息
     *
     * @param
     * @return com.atwk.bean.ResultMsg
     */
    @RequestMapping("/depts")
    @ResponseBody
    public ResultMsg getDepts(){

        //获取部门信息
        List<Department> depts = departmentService.getDepts();

        return ResultMsg.succsee().add("depts",depts);
    }
}
