package com.boot.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ToString
public class User implements Serializable {

    private Long userId;
    private String userName;
    private String userCode;
    private String userPwd;




}
