package com.hcwawe.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hcwawe.reggie.common.R;
import com.hcwawe.reggie.entity.Employee;
import com.hcwawe.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
//        密码进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
//        根据用户名查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null){
            return R.error("登录失败 用户名或者密码错误");
        }
        if (!emp.getPassword().equals(password)){
            return R.error("登陆失败 用户名或者密码错误");
        }
//        查看状态
        if (emp.getStatus() == 0){
            return R.error("账号已禁用");
        }
//        登陆成功
        httpServletRequest.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
//    员工退出登录
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
//        清理Session
        request.getSession().removeAttribute("employee");
        return R.success("退出登录成功");
    }

}
