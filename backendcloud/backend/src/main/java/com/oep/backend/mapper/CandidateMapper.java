package com.oep.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.oep.backend.pojo.Candidate;

@Mapper
public interface CandidateMapper extends BaseMapper<Candidate> {
}
