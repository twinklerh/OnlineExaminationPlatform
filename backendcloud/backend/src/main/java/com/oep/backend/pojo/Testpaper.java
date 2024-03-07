package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Testpaper {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String note;
    private Integer problemCount;
    private Boolean isNeedAppendix;
    private String problems;
    private String enterpriseName;
}
