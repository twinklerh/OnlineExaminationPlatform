package com.oep.backend.serviceImpl.problems.getProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.problems.getProblem.GetProblemByTitleService;
import com.oep.backend.serviceImpl.problems.ClassProblem;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class getProblemByTitleServiceImpl extends ClassProblem implements GetProblemByTitleService {
    @Autowired
    private ProblemMapper problemMapper;
    @Override
    public String getProblemByTitle(Map<String,String> map) {
        int enterpriseId = super.authenticateGetEnterpriseId();
        List<Integer> problemIdList = super.getEnterpriseAllProblemId(enterpriseId);

        if(problemIdList.isEmpty()){  return "数据为空"; }

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.like("title", map.get("problem_title"));
        List<Problem> list = problemMapper.selectList(problemQueryWrapper);
        return WriteValue.writeValueAsString(list);
    }
}
