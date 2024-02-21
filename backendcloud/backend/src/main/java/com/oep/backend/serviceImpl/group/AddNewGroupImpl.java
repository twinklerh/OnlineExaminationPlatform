package com.oep.backend.serviceImpl.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.pojo.Group;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.group.AddNewGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AddNewGroupImpl implements AddNewGroup {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String addNewGroup(Map<String,String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        if(!account.getStatus().equals("enterprise"))   return "身份验证失败";
        String group_name = map.get("group_name");
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id", account.getAccountId());
        Enterprise enterprise = enterpriseMapper.selectOne(enterpriseQueryWrapper);
        Group group = new Group(null, group_name, enterprise.getEnterpriseId());
        groupMapper.insert(group);
        return "成功插入一个分组！";
    }
}
