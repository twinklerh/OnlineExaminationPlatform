package com.oep.backend.serviceImpl.problems.deleteProblem;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.GroupProblem;
import com.oep.backend.pojo.Problem;
import com.oep.backend.service.problems.deleteProblem.DeleteProblemService;
import com.oep.backend.serviceImpl.problems.ClassProblem;
import com.oep.backend.utils.WriteValueAsString;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeleteProblemServiceImpl extends ClassProblem implements DeleteProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    @Override
    public String deleteProblem(Map<String, String> map) {
        int enterpriseId = super.authenticateGetEnterpriseId();
        List<Integer> list = super.getEnterpriseAllProblemId(enterpriseId);
        Integer problem_id = Integer.valueOf(map.get("problem_id"));

        if(!list.contains(problem_id))  return "授权异常！";

        Map<String,String> returnMap = new HashMap<>();
        UpdateWrapper<GroupProblem> groupProblemUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Problem> problemUpdateWrapper = new UpdateWrapper<>();
        groupProblemUpdateWrapper.eq("problem_id",problem_id);
        problemUpdateWrapper.eq("id", problem_id);
        try{
            int resp = groupProblemMapper.delete(groupProblemUpdateWrapper);
            resp = resp + problemMapper.delete(problemUpdateWrapper);
            if(resp != 2)   throw new ExecutorException();
        } catch (ExecutorException e){
            returnMap.put("error_message", "failed");
            return WriteValueAsString.writeValueAsString(returnMap);
        }
        returnMap.put("error_message", "success");
        return WriteValueAsString.writeValueAsString(returnMap);
    }
}
