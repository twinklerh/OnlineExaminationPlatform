package com.oep.backend.serviceImpl.problems;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.GroupProblem;
import com.oep.backend.pojo.Problem;
import com.oep.backend.service.problems.DeleteProblemService;
import com.oep.backend.utils.WriteValueAsString;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteProblemServiceImpl implements DeleteProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    @Override
    public String deleteProblem(Map<String, String> map) {
        String problem_id = map.get("problem_id");
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
