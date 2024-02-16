package com.oep.backend.serviceImpl.submitProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.mapper.GroupProblemsMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Group;
import com.oep.backend.pojo.GroupProblems;
import com.oep.backend.pojo.Problem;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.account.GetAccountInfo;
import com.oep.backend.service.submitProblem.SubmitSubjectService;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubmitSubjectServiceImpl implements SubmitSubjectService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupProblemsMapper groupProblemsMapper;
    @Autowired
    private GroupMapper groupMapper;
    private GetAccountInfo getAccountInfo;
    @Override
    public Map<String, String> addSubjectProblem(Map<String, String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = loginUser.getAccount();
        if(!"enterprise".equals(account.getStatus())) return new HashMap<>() {{ put("error_message", "身份验证异常"); }};

        Map<String,String> returnHashMap = new HashMap<>();

        String title = map.get("title");
        String groupSelect = map.get("groupSelect");
        String description = map.get("description");
        char difficulty = map.get("radioSelectRank").charAt(0);
        boolean ifCheckByHuman = Boolean.parseBoolean(map.get("checkSelect"));
        String rightAnswer = map.get("rightAnswer");
        String appendix_name = map.get("appendix");

        if("".equals(groupSelect))    groupSelect = "default";
        if("".equals(description))    {
            returnHashMap.put("error_message", "题目描述不能为空！");
            return returnHashMap;
        }

        try{
            Problem problem = new Problem(null,title,description,difficulty,ifCheckByHuman,rightAnswer,appendix_name);
            int resp = problemMapper.insert(problem);

            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("group_name", groupSelect);
            Group group = groupMapper.selectOne(groupQueryWrapper);

            if(group==null || resp==0)   throw new ExecutorException();

            GroupProblems groupProblems = new GroupProblems(group.getId(), problem.getId());
            resp = groupProblemsMapper.insert(groupProblems);

            if(resp==0) throw new ExecutorException();

        } catch(ExecutorException e) {
            returnHashMap.put("error_message","更新失败");
            return returnHashMap;
        }

        returnHashMap.put("error_message", "success");
        return returnHashMap;
    }
}
