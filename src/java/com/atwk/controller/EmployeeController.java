package com.atwk.controller;

import com.atwk.bean.Employee;
import com.atwk.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *处理员工CRUD
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 查询员工数据（分页查询）
     * @return
     */
    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //这不是一个分页查询
        //引入pageHelper分页插件
        //查询之前只需要调用，传入页码，以及每页大小
        PageHelper.startPage(pn,10);
        //startPage后面紧跟的这个查询就是分页查询
        List<Employee> employees = employeeService.getAll();
        //使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行。
        //封装了详细的分页信息，包括查询出来的数据,传入连续显示的页数
        PageInfo pageInfo = new PageInfo(employees,5);

        model.addAttribute("pageInfo",pageInfo);
        return "list";
    }
}
