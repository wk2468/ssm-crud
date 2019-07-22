
//查询的总记录数
var totalRecord;

//当前页
var currentPage;

//用户名校验状态
var checkUserStatus;

//页面加载完成以后，直接发送ajax请求，获取分页数据
$(function () {
    //去首页
    to_page(1);

    //点击新增按钮弹出模态框
    $("#emp_add_modal_btn").click(function () {

        //先重置表单
        reset_form("#empAddModal form");
        // $("#empAddModal form")[0].reset();

        //发送ajax请求，查出部门信息，显示在下拉列表中
        getDepts("add");

        //弹出模态框
        $("#empAddModal").modal({
            backdrop : "static"
        })
    });

    //新增时点击保存按钮
    $("#emp_add_save_btn").click(function () {

        //模态框中填写的表单数据提交给服务器进行保存
        // 1.校验表单数据
        if(!validate_add_form()){
            return false;
        }

        //2.判断ajax用户名校验是否成功
        if (!checkUserStatus){
            return false;
        }

        //3.发送ajax请求保存员工
        $.ajax({
            url: "http://localhost:8080/ssm_crud/employee/emp",
            type: "POST",
            dataType: "json",
            data: $("#empAddModal form").serialize(),
            success: function (result) {

                if (result.code == 100) {
                    //员工保存成功：
                    //1.关闭模态框
                    $("#empAddModal").modal('hide');
                    //2.来到最后一页，显示刚保存的数据
                    to_page(totalRecord);
                } else {
                    //显示错误信息
                    if (undefined != result.extend.errorFields.email) {
                        //显示邮箱错误信息
                        show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
                    }
                    if (undefined != result.extend.errorFields.empName) {
                        //显示员工姓名错误信息
                        show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
                    }

                }

            }
        })
    });

    //点击编辑按钮弹出模态框
    $(document).on("click", ".editBtn", function () {

        //先重置表单
        reset_form("#empUpdateModal form");
        //1.发送ajax请求，查出部门信息，显示在下拉列表中
        getDepts("update");
        //2.查询并显示员工信息
        getEmp($(this).attr("edit-id"));

        //3.把员工id传递给更新按钮
        $("#emp_update_save_btn").attr("edit-id", $(this).attr("edit-id"));

        //弹出模态框
        $("#empUpdateModal").modal({
            backdrop : "static"
        })
    });

    //编辑时点击更新按钮
    $("#emp_update_save_btn").click(function () {

        //模态框中填写的表单数据提交给服务器进行保存
        //2.校验邮箱
        var email = $("#email_update_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!regEmail.test(email)){
            show_validate_msg("#email_update_input","error","邮箱格式不正确");
            return false;
        }else{
            show_validate_msg("#email_update_input","success","");
        }

        //3.发送ajax请求更新员工
        $.ajax({
            url: "http://localhost:8080/ssm_crud/employee/emp/" + $(this).attr("edit-id"),
            type: "PUT",
            dataType: "json",
            data: $("#empUpdateModal form").serialize(),
            success: function (result) {

                if (result.code == 100) {
                    //员工保存成功：
                    //1.关闭模态框
                    $("#empUpdateModal").modal('hide');
                    //2.来到当前页，显示刚保存的数据
                    to_page(currentPage);
                }
            }
        })
    });

    //点击删除按钮
    //单个删除
    $(document).on("click", ".deleteBtn", function () {

        //弹出是否确认删除对话框
        if (confirm("确认删除【" + $(this).parents("tr").find("td:eq(2)").text() + "】？")) {
            //2.发送ajax请求更新员工
            $.ajax({
                url: "http://localhost:8080/ssm_crud/employee/emp/" + $(this).attr("del-id"),
                type: "DELETE",
                dataType: "json",
                success: function (result) {

                    if (result.code == 100) {
                        //员工删除成功：
                        alert("处理成功！")
                        //2.来到最后一页，显示刚保存的数据
                        to_page(currentPage);
                    }
                }
            })
        };
    });

    //校验用户名是否可用
    $("#empName_add_input").change(function () {
        //发送ajax请求校验用户名是否可用
        var empName = this.value;
        $.ajax({
            url: "http://localhost:8080/ssm_crud/employee/checkuser",
            data: "empName=" + empName,
            type: "POST",
            success: function (result) {

                if (result.code == "100"){
                    show_validate_msg("#empName_add_input","success","用户名可用");
                    checkUserStatus = true;
                }else {
                    show_validate_msg("#empName_add_input","error",result.extend.va_msg);
                    checkUserStatus = false;
                }
            }
        });
    });

    //完成全选和换不选功能
    $("#check_all").click(function () {

        //attr获取checked是undefined，attr能够获取自定义属性的值
        //prop修改和读取dom原生属性的值
        //$(this).prop("checked");
        $(".check_item").prop("checked", $(this).prop("checked"));
    });

    //check_item
    $(document).on("click", ".check_item", function () {

        //选中的checkbox等于全部checkbox
        var flg = $(".check_item:checked").length == $(".check_item").length;
        //全选按钮随着checkbox全部选中而选中
        $("#check_all").prop("checked", flg);
    });

    //批量删除
    $("#emp_delete_all_btn").click(function () {

        var empNames = "";
        var empIds = "";
        $.each($(".check_item:checked"), function () {

            empNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
            empIds += $(this).parents("tr").find("td:eq(1)").text() + "-";

        });

        //去除empNames多余的，
        empNames = empNames.substring(0, empNames.length-1);

        if (confirm("确认删除【" + empNames + "】？")) {
            //发送ajax请求批量删除员工
            $.ajax({

                url: "http://localhost:8080/ssm_crud/employee/emp/" + empIds,
                type: "DELETE",
                dataType: "json",
                success: function (result) {
                    if (result.code == 100) {
                        //员工删除成功：
                        alert("处理成功！")
                        //2.来到最后一页，显示刚保存的数据
                        to_page(currentPage);
                    }
                }

            });
        }
    })
});



function getEmp(id) {

    $.ajax({
        url: "http://localhost:8080/ssm_crud/employee/emp/" + id,
        type: "GET",
        success: function (result) {

            var emp = result.extend.emp;
            $("#empName_update_input").val(emp.empName);
            $("#email_update_input").val(emp.email);
            $("#empUpdateModal input[name=gender]").val([emp.gender]);
            $("#dept_update").val([emp.dId]);

        }
    });
}

/**
 * 查出所有的部门信息并显示在下拉列表中
 */
function getDepts(ele) {
    $.ajax({
        url: "http://localhost:8080/ssm_crud/department/depts",
        type: "GET",
        success: function (result) {

            if (ele == "add") {
                //清空
                $("#empAddModal select").empty();

                //遍历部门信息
                $.each(result.extend.depts,function () {
                    var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId);
                    optionEle.appendTo("#empAddModal select");
                })
            } else {

                //清空
                $("#empUpdateModal select").empty();

                //遍历部门信息
                $.each(result.extend.depts,function () {
                    var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId);
                    optionEle.appendTo("#empUpdateModal select");
                })
            }
        }
    });
}

/**
 * 分页查询并显示员工列表
 * @param pn 页码
 */
function to_page(pn) {
    $.ajax({
        url: "http://localhost:8080/ssm_crud/employee/emps",
        data: "pn="+pn,
        type: "GET",
        success: function (result) {
            // console.log(result);
            //1：解析并显示员工数据
            build_emps_table(result);
            //2: 解析并显示分页信息
            build_page_info(result);
            //3：解析并显示分页条数据
            build_page_nav(result);
        }
    });
}

/**
 * 解析显示员工信息
 * @param result
 */
function build_emps_table(result) {

    //清空
    $("#emps_table tbody").empty();

    var emps = result.extend.pageInfo.list;
    $.each(emps,function (index,item) {
        var checkboxTd = $("<td><input type='checkbox' class='check_item' /></td>");
        var empIdTd = $("<td></td>").append(item.empId);
        var empNameTd = $("<td></td>").append(item.empName);
        var genderTd = $("<td></td>").append(item.gender== "M" ? "男" : "女");
        var emailTd = $("<td></td>").append(item.email);
        var deptNameTd = $("<td></td>").append(item.department.deptName);
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm editBtn")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
            .append("编辑");
        //为编辑按钮添加一个自定义属性，来表示当前员工id
        editBtn.attr("edit-id",item.empId);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm deleteBtn")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash"))
            .append("删除");
        //为编辑按钮添加一个自定义属性，来表示当前员工id
        delBtn.attr("del-id",item.empId);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);

        $("<tr></tr>").append(checkboxTd)
            .append(empIdTd)
            .append(empNameTd)
            .append(genderTd)
            .append(emailTd)
            .append(deptNameTd)
            .append(btnTd)
            .appendTo("#emps_table tbody");
    });

}

/**
 * 解析显示分页信息
 * @param result
 */
function build_page_info(result) {

    //清空
    $("#page_info_area").empty();

    $("#page_info_area").append("当前第" + result.extend.pageInfo.pageNum + "页，总" + result.extend.pageInfo.pages+ "页，总"+result.extend.pageInfo.total+"条数");

    totalRecord = result.extend.pageInfo.total;
    currentPage = result.extend.pageInfo.pageNum;
}

/**
 * 解析显示分页条,点击分页要能去到下一页
 * @param result
 */
function build_page_nav(result) {

    //清空
    $("#page_nav_area").empty();

    var ul = $("<ul></ul>").addClass("pagination");

    //构建元素
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //没有前一页
    if(result.extend.pageInfo.hasPreviousPage == false){
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加翻页事件
        firstPageLi.click(function () {
            to_page(1);
        });
        prePageLi.click(function () {
            to_page(result.extend.pageInfo.pageNum-1);
        });
    }

    //添加首页和前页的提示
    ul.append(firstPageLi).append(prePageLi);

    //1,2,3,4,5
    $.each(result.extend.pageInfo.navigatepageNums,function (index,item) {

        var numLi = $("<li></li>").append($("<a></a>").append(item));
        //当前页
        if(result.extend.pageInfo.pageNum == item){
            numLi.addClass("active");
        } else {
            numLi.click(function () {
                to_page(item);
            });
        }
        ul.append(numLi);
    });

    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
    //没有下一页
    if(result.extend.pageInfo.hasNextPage == false){
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        //为元素添加翻页事件
        nextPageLi.click(function () {
            to_page(result.extend.pageInfo.pageNum + 1);
        });
        lastPageLi.click(function () {
            to_page(result.extend.pageInfo.pages);
        });
    }

    //添加下一页和末页的提示
    ul.append(nextPageLi).append(lastPageLi);

    //把ul加入到nav中
    var navEle = $("<nav></nav>").append(ul);

    navEle.appendTo("#page_nav_area");
}

/**
 * 重置表单
 * @param ele
 */
function reset_form(ele) {

    //清空表单内容
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success")
    $(ele).find(".help-block").text("");
}

/**
 * 校验表单数据
 * @returns {boolean}
 */
function validate_add_form(){
    //1.拿到要校验的数据，使用正则表达式
    var empName = $("#empName_add_input").val();
    var regEmpName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
    if(!regEmpName.test(empName)){
        show_validate_msg("#empName_add_input","error","用户名必须是2-5为汉字或者6-16为英文和数字的组合");
        return false;
    }else{
        show_validate_msg("#empName_add_input","success","");
    }

    //2.校验邮箱
    var email = $("#email_add_input").val();
    var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if(!regEmail.test(email)){
        show_validate_msg("#email_add_input","error","邮箱格式不正确");
        return false;
    }else{
        show_validate_msg("#email_add_input","success","");
    }

    return true;
}

/**
 * 显示校验状态
 * @param ele
 * @param status
 * @param msg
 */
function show_validate_msg(ele,status,msg) {

    //先清除当前元素的校验状态
    $(ele).parent().removeClass("has-error has-success");
    $(ele).next("span").text("");

    //显示校验状态
    if("error" == status){
        $(ele).parent().addClass("has-error");
        $(ele).next("span").text(msg);
    }else if ("success" == status){
        $(ele).parent().addClass("has-success");
        $(ele).next("span").text(msg);
    }
}