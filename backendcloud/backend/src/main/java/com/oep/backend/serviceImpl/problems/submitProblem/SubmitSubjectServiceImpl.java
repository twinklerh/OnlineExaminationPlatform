package com.oep.backend.serviceImpl.problems.submitProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.problems.submitProblem.SubmitSubjectService;
import com.oep.backend.utils.WriteValue;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SubmitSubjectServiceImpl implements SubmitSubjectService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    @Autowired
    private GroupMapper groupMapper;
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String addSubjectProblem(Map<String, String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = loginUser.getAccount();
        if(!"enterprise".equals(account.getStatus())) return WriteValue.writeValueAsString(new HashMap<>() {{ put("error_message", "身份验证异常"); }});
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper);
        Map<String,String> returnHashMap = new HashMap<>();

        String title = map.get("title");
        String groupSelect = map.get("groupSelect");
        String description = map.get("description");
        String difficulty = map.get("radioSelectRank");
        String checkBy = map.get("checkSelect");
        String rightAnswer = map.get("rightAnswer");
        if(!isFloat(map.get("score")))    {
            returnHashMap.put("error_message", "分值的输入格式有误");
            return WriteValue.writeValueAsString(returnHashMap);
        }
        Float score = Float.valueOf(map.get("score"));

        if("".equals(description)) {
            returnHashMap.put("error_message", "题目描述不能为空！");
            return WriteValue.writeValueAsString(returnHashMap);
        }

        try{
            Problem problem = new Problem(null,title,description,difficulty,checkBy,rightAnswer, null,"综合", score, enterprise.getEnterpriseId());
            int resp = problemMapper.insert(problem);
            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("group_name", groupSelect);
            Group group = groupMapper.selectOne(groupQueryWrapper);
            if(group==null || resp==0)   throw new ExecutorException();

            GroupProblem groupProblem = new GroupProblem(group.getId(), problem.getId());
            resp = groupProblemMapper.insert(groupProblem);

            if(resp==0) throw new ExecutorException();

        } catch(ExecutorException e) {
            returnHashMap.put("error_message","更新失败");
            return WriteValue.writeValueAsString(returnHashMap);
        }

        returnHashMap.put("error_message", "success");
        return WriteValue.writeValueAsString(returnHashMap);
    }
    public static boolean isFloat(String str) {
        if (null == str || str.isEmpty()) return false;
        Pattern pattern = Pattern.compile("^[-+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
