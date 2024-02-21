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
import com.oep.backend.utils.WriteValueAsString;
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
        List<Integer> groupIdList = groupMapper.selectList(groupQueryWrapper).stream().map(Group::getId).toList();

        QueryWrapper<GroupProblem> groupProblemQueryWrapper = new QueryWrapper<>();
        groupProblemQueryWrapper.in("group_id", groupIdList);
        List<Integer> problemIdList = groupProblemMapper.selectList(groupProblemQueryWrapper).stream().map(GroupProblem::getProblemId).toList();

        if(problemIdList.isEmpty()){  return "数据为空"; }

        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.in("id", problemIdList);
        List<Problem> list = problemMapper.selectList(problemQueryWrapper);

        return WriteValueAsString.writeValueAsString(list);
    }
}
