package com.lhw.dubbo_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private String name;
    private Integer id;
    private Integer age;

}
