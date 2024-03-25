package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @TableId(type = IdType.AUTO)
    private Integer candidateId;
    private String fullname;
    private String headphoto;
    private char sex;
    private String email;
    private String telephone;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;
    private String accountId;
}
