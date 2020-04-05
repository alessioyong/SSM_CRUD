package com.yxx.crud.controller;

import com.yxx.crud.bean.Department;
import com.yxx.crud.bean.Msg;
import com.yxx.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理部门有关的请求
 */
@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    /**
     * 返回所有的部门信息
     *
     */
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts(){
        //查出的所有部门的信息
        List<Department> list=departmentService.getDepts();
        return Msg.success().add("depts",list);
    }

}
