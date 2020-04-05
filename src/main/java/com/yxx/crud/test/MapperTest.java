package com.yxx.crud.test;


import com.yxx.crud.bean.Department;
import com.yxx.crud.dao.DepartmentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;
    /**
     * 测试DepartmentMapper
     */
    @Test
    public void testCRUD(){
        System.out.println(departmentMapper);

       /* departmentMapper.insertSelective(new Department(null,"测试部"));*/
    }
}
