package com.yxx.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxx.crud.bean.Employee;
import com.yxx.crud.bean.Msg;
import com.yxx.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理员工的CRUD
 */
@Controller
public class EmployeeController{

    @Autowired
    EmployeeService employeeService;

    @ResponseBody
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            List<Integer> del_ids=new ArrayList<>();
            String[] str_ids= ids.split("-");
            for (String string:str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBath(del_ids);
        }else{
            Integer id=Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }

        return Msg.success();
    }


    /**
     * 员工更新方法
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee){
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee=employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }



    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkuser(String empName){

      String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名可以是6-16位因为和数字的组合或者2-5位中文");
        }

       boolean b= employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg","用戶名不可用");
        }


    }



    /**
     * 员工保存
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
       if(result.hasErrors()){
           //校验失败，返回错误信息，在模态框中
           Map<String ,Object> map=new HashMap<String,Object>();
           List<FieldError> errors=result.getFieldErrors();
           for (FieldError fieldError:errors){
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
           }
           return Msg.fail().add("errorFields",map);
       }else{
           employeeService.saveEmp(employee);
           return Msg.success();
       }

    }


    /**
     * 导入jackson包
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,5);
        List<Employee> emps=employeeService.getAll();

        PageInfo page=new PageInfo(emps,5);

        return Msg.success().add("pageInfo",page);

    }

   // @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,5);
        List<Employee> emps=employeeService.getAll();

        PageInfo page=new PageInfo(emps,5);
        model.addAttribute("pageinfo",page);
        return "list";
    }
}
