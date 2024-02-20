package com.oep.backend.serviceImpl.submitProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Group;
import com.oep.backend.pojo.GroupProblem;
import com.oep.backend.pojo.Problem;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.submitProblem.SubmitObjectService;
import com.oep.backend.utils.WriteValueAsString;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubmitObjectServiceImpl implements SubmitObjectService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Override
    public String addObjectProblem(Map<String, String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();

        Account account = userDetails.getAccount();

        if(!"enterprise".equals(account.getStatus())) return WriteValueAsString.writeValueAsString(new HashMap<>() {{ put("error_message", "身份验证异常"); }});

        Map<String,String> returnHashMap = new HashMap<>();

        String problemType = map.get("problemType");
        String title = map.get("title");
        String groupSelect = map.get("groupSelect");
        String description = map.get("description");
        String rightAnswer = map.get("rightAnswer");
        String difficulty = map.get("radioSelectRank");
        String checkBy = map.get("checkSelect");
        String appendix_name =map.get("selectAnswer");

        if("select".equals(problemType))  problemType = "选择";
        else if ("judge".equals(problemType))   problemType = "判断";
        else if ("filling".equals(problemType)) problemType = "填空";

        if(!"选择".equals(problemType))    appendix_name = "";

        try{
            Problem problem = new Problem(null,title,description,difficulty,checkBy,rightAnswer,appendix_name,problemType,0,0);

            int resp = problemMapper.insert(problem);
            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("group_name", groupSelect);
            Group group = groupMapper.selectOne(groupQueryWrapper);
            if(group==null || resp==0)   throw new ExecutorException();

            GroupProblem groupProblem = new GroupProblem(group.getId(), problem.getId());
            resp = groupProblemMapper.insert(groupProblem);

            if(resp==0)  throw new ExecutorException();
        } catch (ExecutorException e) {
            returnHashMap.put("error_message", "更新失败");
            return WriteValueAsString.writeValueAsString(returnHashMap);
        }
        returnHashMap.put("error_message", "success");
        return WriteValueAsString.writeValueAsString(returnHashMap);
    }

}
