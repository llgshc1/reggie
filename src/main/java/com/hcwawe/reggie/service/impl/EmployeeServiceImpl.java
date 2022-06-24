package com.hcwawe.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hcwawe.reggie.entity.Employee;
import com.hcwawe.reggie.mapper.EmployeeMapper;
import com.hcwawe.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
