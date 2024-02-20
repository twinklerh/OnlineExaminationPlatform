package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {
    @TableId(type = IdType.AUTO)
    private Integer enterpriseId;
    private String name;
    private String email;
    private String accountId;
}
