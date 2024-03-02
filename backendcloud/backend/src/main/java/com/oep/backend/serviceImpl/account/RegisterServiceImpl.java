package com.oep.backend.serviceImpl.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.AccountMapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.service.account.RegisterService;
import com.oep.backend.pojo.Account;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    public AccountMapper accountMapper;
    @Autowired
    public CandidateMapper candidateMapper;
    private final Map<String, String> map = new HashMap<>();
    @Override
    public String addAccount(String accountId, String password, String confirmPassword) {
        if(accountId == null) {
            map.put("error_message", "用户名不能为空");
            return WriteValue.writeValueAsString(map);
        }
        accountId = accountId.trim();
        if(accountId.isEmpty()) {
            map.put("error_message", "用户名不能为空");
            return WriteValue.writeValueAsString(map);
        }
        if(password.isEmpty()){
            map.put("error_message", "密码不能为空");
            return WriteValue.writeValueAsString(map);
        }
        if(!password.equals(confirmPassword)){
            map.put("error_message", "两次输入的密码不一致");
            return WriteValue.writeValueAsString(map);
        }
        if(accountId.length()>19){
            map.put("error_message", "用户名过长");
            return WriteValue.writeValueAsString(map);
        }
        if(password.length()>=16){
            map.put("error_message", "密码长度不能超过16位");
            return WriteValue.writeValueAsString(map);
        }
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", accountId);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if(!accounts.isEmpty()) {
            map.put("error_message", "该用户名已存在");
            return WriteValue.writeValueAsString(map);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        Account account = new Account(accountId, encodedPassword, "candidate");
        accountMapper.insert(account);
        //  下面往从表中插入数据
        Candidate candidate = new Candidate();
        candidate.setAccountId(accountId);
        candidateMapper.insert(candidate);
        map.put("error_message", "register successfully!");
        return WriteValue.writeValueAsString(map);
    }
}
