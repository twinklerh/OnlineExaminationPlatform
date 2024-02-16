package com.oep.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oep.backend.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
}
