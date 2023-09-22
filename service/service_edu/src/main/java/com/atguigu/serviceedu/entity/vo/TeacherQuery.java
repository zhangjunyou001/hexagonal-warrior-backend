package com.atguigu.serviceedu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer level;

    private String begin;

    private String end;
}
