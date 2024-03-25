package com.oep.backend.serviceImpl.userInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.user_info.UpdateCandidateInfoService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateCandidateInfoServiceImpl implements UpdateCandidateInfoService {
    @Autowired
    private CandidateMapper candidateMapper;
    @Override
    public String updateCandidateInfo(Map<String, String> map) {
        Account account = getAccount();
        Candidate candidate = getCandidate(account);

        Map<String, String> respMap = new HashMap<>();

        char sex = map.get("sex").charAt(0);
        String email = map.get("email");
        String telephone = map.get("telephone");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthdayString = map.get("birthday");
        Date birthday;
        try{
            birthday = dateFormat.parse(birthdayString); // 解析字符串为Date对象
        } catch (ParseException e) {
            respMap.put("error_message", "日期格式错误");
            return WriteValue.writeValueAsString(respMap);
        }
        UpdateWrapper<Candidate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("candidate_id", candidate.getCandidateId());
        updateWrapper.set("sex", sex);
        updateWrapper.set("email", email);
        updateWrapper.set("telephone", telephone);
        updateWrapper.set("birthday", birthday);
        candidateMapper.update(updateWrapper);
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private Account getAccount()    {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
    private Candidate getCandidate(Account account)    {
        QueryWrapper<Candidate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        return candidateMapper.selectOne(queryWrapper);
    }
}
