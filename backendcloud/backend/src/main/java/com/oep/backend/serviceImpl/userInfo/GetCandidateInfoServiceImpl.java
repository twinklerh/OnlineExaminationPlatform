package com.oep.backend.serviceImpl.userInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.user_info.GetCandidateInfoService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetCandidateInfoServiceImpl implements GetCandidateInfoService {
    @Autowired
    private CandidateMapper candidateMapper;
    @Override
    public String getCandidateInfo() {
        Map<String, String> respMap = new HashMap<>();
        Account account = getAccount();
        if(!"candidate".equals(account.getStatus()))     {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        QueryWrapper<Candidate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        Candidate candidate = candidateMapper.selectOne(queryWrapper);
        respMap.put("error_message", "success");
        respMap.put("candidate", WriteValue.writeValueAsString(candidate));
        return WriteValue.writeValueAsString(respMap);
    }
    private Account getAccount()    {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
}
