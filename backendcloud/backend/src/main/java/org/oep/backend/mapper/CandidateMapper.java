package org.oep.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.oep.backend.pojo.Candidate;

@Mapper
public interface CandidateMapper extends BaseMapper<Candidate> {
}
