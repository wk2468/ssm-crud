package com.atwk.controller;

import com.atwk.bean.Employee;
import com.atwk.bean.ResultMsg;
import com.atwk.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 处理员工CRUD
 *
 * @author wk
 */
@RequestMapping("/employee")
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 删除员工(单个删除/批量删除)
     *
     * @param ids
     * @return com.atwk.bean.ResultMsg
     */
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResultMsg delEmp(@PathVariable("ids") String ids) {

        if (ids.contains("-")) {

            List<Integer> delIds = new ArrayList<>();
            String[] strIds = ids.split("-");

            for (String strId : strIds) {

                delIds.add(Integer.parseInt(strId));
            }

            employeeService.delEmpBatch(delIds);

        } else {
            //单个删除
            Integer del_id = Integer.parseInt(ids);
            employeeService.delEmp(del_id);

        }

        return ResultMsg.succsee();
    }

    /**
     * @param employee 员工信息
     * @return com.atwk.bean.ResultMsg
     * @Description 员工保存
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg saveEmp(@Valid Employee employee, BindingResult result) {

        if (result.hasErrors()) {
            //校验失败应该返回失败信息，在模态框中显示错误信息
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段名：" + fieldError.getField());
                System.out.println("错误信息：" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResultMsg.fail().add("errorFields", map);

        } else {
            employeeService.saveEmp(employee);
            return ResultMsg.succsee();
        }
    }

    /**
     * @Description 查询员工信息，回显
     *
     * @param id 员工id
     * @return ResultMsg
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg getEmp(@PathVariable("id") Integer id) {

        //获取员工信息
        Employee employee = employeeService.getEmp(id);

        return ResultMsg.succsee().add("emp",employee);
    }

    /**
     * @Description 更新员工信息
     *
     * @param employee 员工信息
     * @return ResultMsg
     */
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResultMsg updateEmp(Employee employee) {

        employeeService.updateEmp(employee);
        return ResultMsg.succsee();
    }

    /**
     * 分页查询员工信息，返回json数据
     *
     * @param pn
     * @return ResultMsg
     */
    @RequestMapping("/emps")
    @ResponseBody
    public ResultMsg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        //引入pageHelper分页插件
        //查询之前只需要调用，传入页码，以及每页大小
        PageHelper.startPage(pn, 10);
        //startPage后面紧跟的这个查询就是分页查询
        List<Employee> employees = employeeService.getAll();
        //使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行。
        //封装了详细的分页信息，包括查询出来的数据,传入连续显示的页数
        PageInfo<Employee> pageInfo = new PageInfo<>(employees, 5);

        return ResultMsg.succsee().add("pageInfo", pageInfo);
    }

    /**
     * @param empName 员工姓名
     * @return com.atwk.bean.ResultMsg
     * @Description 校验用户是否可用
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public ResultMsg checkUser(String empName) {

        //先判断用户名是否合法
        String regEmpName = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regEmpName)) {

            return ResultMsg.fail().add("va_msg", "用户名必须是2-5为汉字或者6-16为英文和数字的组合");
        }

        boolean isTrue = employeeService.checkUser(empName);

        if (isTrue) {
            return ResultMsg.succsee();
        } else {
            return ResultMsg.fail().add("va_msg", "用户名不可用");
        }
    }

    /**
     * 查询员工数据（分页查询）
     *
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //这不是一个分页查询
        //引入pageHelper分页插件
        //查询之前只需要调用，传入页码，以及每页大小
        PageHelper.startPage(pn, 10);
        //startPage后面紧跟的这个查询就是分页查询
        List<Employee> employees = employeeService.getAll();
        //使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行。
        //封装了详细的分页信息，包括查询出来的数据,传入连续显示的页数
        PageInfo<Employee> pageInfo = new PageInfo<>(employees, 5);

        model.addAttribute("pageInfo", pageInfo);
        return "list";
    }
}
