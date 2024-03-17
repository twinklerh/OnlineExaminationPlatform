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
public class CandidateProblem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer problemId;
    private Integer candidateId;
    private Integer examId;
    private String candidateAnswer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date dateTime;
    private Float getScore;
    private Boolean isChecked;
}
