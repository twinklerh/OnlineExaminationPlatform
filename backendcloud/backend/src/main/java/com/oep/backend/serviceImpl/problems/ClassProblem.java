package com.oep.backend.serviceImpl.problems;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.security.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassProblem {
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    public int authenticateGetEnterpriseId() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();

        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id",account.getAccountId());
        return enterpriseMapper.selectOne(enterpriseQueryWrapper).getEnterpriseId();
    }
    public List<Integer> getEnterpriseAllProblemId(int enterpriseId) {
        QueryWrapper<Group>groupQueryWrapper = new QueryWrapper<>();
        groupQueryWrapper.eq("enterprise_id", enterpriseId);
        List<Integer> groupIdList = groupMapper.selectList(groupQueryWrapper).stream().map(Group::getId).toList();

        QueryWrapper<GroupProblem> groupProblemQueryWrapper = new QueryWrapper<>();
        groupProblemQueryWrapper.in("group_id", groupIdList);
        return groupProblemMapper.selectList(groupProblemQueryWrapper).stream().map(GroupProblem::getProblemId).toList();
    }
}
