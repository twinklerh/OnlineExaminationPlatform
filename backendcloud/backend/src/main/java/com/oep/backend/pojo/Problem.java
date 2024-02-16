package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private char difficulty;
    private boolean ifCheckByHuman;
    private String answer;
    private String appendixName;
}
