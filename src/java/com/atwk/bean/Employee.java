package com.atwk.bean;


import javax.validation.constraints.Pattern;

public class Employee {

    private Integer empId;

    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})",
                message = "用户名必须是2-5为汉字或者6-16为英文和数字的组合")
    private String empName;

    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",
                message = "邮箱格式不正确")
    private String email;

    private String gender;

    private Integer dId;

    private Department department;


    public Employee() {
    }

    public Employee(Integer empId, String empName, String email, String gender, Integer dId) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.gender = gender;
        this.dId = dId;
    }

    public Employee(Integer empId, String empName, String email, String gender, Integer dId, com.atwk.bean.Department department) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.gender = gender;
        this.dId = dId;
        this.department = department;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public com.atwk.bean.Department getDepartment() {
        return department;
    }

    public void setDepartment(com.atwk.bean.Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", dId=" + dId +
                ", department=" + department +
                '}';
    }
}