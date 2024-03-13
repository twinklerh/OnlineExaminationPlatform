package com.oep.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateExam {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer candidateId;
    private Integer examId;
    private float score;
    private String appendix;
    private Boolean isJoined;
}
