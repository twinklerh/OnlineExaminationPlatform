package com.oep.backend.serviceImpl.userInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.oep.backend.service.user_info.GetEnterpriseInfoService;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetEnterpriseInfoServiceImpl implements GetEnterpriseInfoService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String getEnterpriseInfo() {
        Account account = getAccount();
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper);
        Map<String, String> respMap = new HashMap<>();
        respMap.put("enterprise_user", WriteValue.writeValueAsString(enterprise));
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private Account getAccount()    {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
}
