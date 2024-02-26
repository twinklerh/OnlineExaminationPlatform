package com.oep.backend.serviceImpl.problems.getProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.Problem;
import com.oep.backend.service.problems.getProblem.GetAProblemService;
import com.oep.backend.serviceImpl.problems.ClassProblem;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetAProblemServiceImpl extends ClassProblem implements GetAProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Override
    public String GetAProblemById(Map<String, String> map) {
        int enterpriseId = super.authenticateGetEnterpriseId();
        List<Integer> problemIdList = super.getEnterpriseAllProblemId(enterpriseId);
        Integer problem_id = Integer.parseInt(map.get("problem_id"));
        if(!problemIdList.contains(problem_id))  return "非法访问";
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", problem_id);
        Problem problem = problemMapper.selectOne(queryWrapper);
        return WriteValue.writeValueAsString(problem);
    }
}
