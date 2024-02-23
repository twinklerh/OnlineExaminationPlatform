package com.oep.backend.serviceImpl.problems.getProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.problems.getProblem.GetAllProblemService;
import com.oep.backend.serviceImpl.problems.ClassProblem;
import com.oep.backend.utils.WriteValueAsString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllProblemServiceImpl extends ClassProblem implements GetAllProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Override
    public String getAllProblems() {
        int enterpriseId = super.authenticateGetEnterpriseId();
        List<Integer> problemIdList = super.getEnterpriseAllProblemId(enterpriseId);
        if(problemIdList.isEmpty()){  return "数据为空"; }

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.in("id", problemIdList);
        List<Problem> list = problemMapper.selectList(problemQueryWrapper);

        return WriteValueAsString.writeValueAsString(list);
    }
}
