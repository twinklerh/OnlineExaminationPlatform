package com.oep.backend.serviceImpl.problems;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.Problem;
import com.oep.backend.service.problems.GetAProblemService;
import com.oep.backend.utils.WriteValueAsString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetAProblemServiceImpl implements GetAProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Override
    public String GetAProblemById(Map<String, String> map) {
        int problem_id = Integer.parseInt(map.get("problem_id"));
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", problem_id);
        Problem problem = problemMapper.selectOne(queryWrapper);
        return WriteValueAsString.writeValueAsString(problem);
    }
}
