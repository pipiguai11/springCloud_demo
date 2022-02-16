package com.lhw.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：linhw
 * @date ：22.2.16 10:05
 * @description：用户传参DTO
 * @modified By：
 */
@ApiModel(description = "用户传参DTO对象")
@Data
public class UserDTO implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "年龄")
    private int age;

    @ApiModelProperty(value = "详细地址")
    private String addr;

}
