package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "group_problem")
public class GroupProblem {
    private Integer groupId;
    private Integer problemId;
}
