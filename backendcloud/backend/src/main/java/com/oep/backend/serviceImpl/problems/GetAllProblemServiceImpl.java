package com.oep.backend.serviceImpl.problems;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.problems.GetAllProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllProblemServiceImpl implements GetAllProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    @Override
    public String getAllProblems() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();

        QueryWrapper<Enterprise>enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id",account.getAccountId());
        int enterpriseId = enterpriseMapper.selectOne(enterpriseQueryWrapper).getEnterpriseId();

        QueryWrapper<Group>groupQueryWrapper = new QueryWrapper<>();
        groupQueryWrapper.eq("enterprise_id", enterpriseId);
        List<Group> groupList = groupMapper.selectList(groupQueryWrapper);

        QueryWrapper<GroupProblem> groupProblemQueryWrapper = new QueryWrapper<>();
        for(Group i: groupList){ groupProblemQueryWrapper.eq("group_id", i.getId()); }
        List<Integer> problemIdList =
            groupProblemMapper.selectList(groupProblemQueryWrapper).stream().map(GroupProblem::getProblemId).toList();

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.in("id", problemIdList); //查询problem中id在groupProblemList中的所有元素
        List<Problem> list = problemMapper.selectList(problemQueryWrapper);
        System.out.println(list);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr;
        try {
            jsonStr = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            System.out.println("jsonStr转换出现异常 GetAllProblemServiceImpl.java line:57");
            return null;
        }
        System.out.println("line-60:" + jsonStr);
        return jsonStr;
    }
}
