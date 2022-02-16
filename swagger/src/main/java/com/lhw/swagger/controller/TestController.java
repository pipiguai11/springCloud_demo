package com.lhw.swagger.controller;

import com.lhw.swagger.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：linhw
 * @date ：22.2.16 10:00
 * @description：
 * @modified By：
 */
@Api("REST Test-controller")
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    @ApiOperation("第一个测试接口，返回一个success")
    public String first(){
        return "success";
    }

    @RequestMapping(value = "/second", method = RequestMethod.POST)
    @ApiOperation("第二个测试接口，接收一个用户对象")
    public UserDTO second(UserDTO userDTO){
        userDTO.setAge(userDTO.getAge() + 1);
        userDTO.setUsername("change : " + userDTO.getAddr());
        userDTO.setAddr("real : " + userDTO.getAddr());
        return userDTO;
    }

}
