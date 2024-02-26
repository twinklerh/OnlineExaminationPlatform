package com.oep.backend.serviceImpl.papertest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.security.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ClassTestPaper {
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    public Account authenticate(){
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
    public Enterprise getEnterpriseName(Account account){
        String account_id = account.getAccountId();
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account_id);
        return enterpriseMapper.selectOne(queryWrapper);
    }

}
