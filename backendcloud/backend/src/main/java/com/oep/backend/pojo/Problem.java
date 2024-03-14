package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "problem")
public class Problem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String difficulty;
    private String checkBy;
    private String rightAnswer;
    private String appendixName;
    private String type;
    private Float score;
    private Integer enterpriseId;
}
