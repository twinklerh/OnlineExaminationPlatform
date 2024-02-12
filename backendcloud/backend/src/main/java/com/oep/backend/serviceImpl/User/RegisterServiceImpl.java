package com.oep.backend.serviceImpl.User;

import com.oep.backend.mapper.AccountMapper;
import com.oep.backend.service.RegisterService;
import com.oep.backend.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    public AccountMapper accountMapper;
    private Map<String, String> map;
    @Override
    public Map<String, String> addAccount(String accountId, String password, String confirmPassword) {
        if(accountId == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        accountId = accountId.trim();
        if(accountId.isEmpty()) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if(password == null){
            map.put("error_message", "密码不能为空");
            return map;
        }
        if(!password.equals(confirmPassword)){
            map.put("error_message", "两次输入的密码不一致");
            return map;
        }
        if(accountId.length()>19){
            map.put("error_message", "用户名过长");
            return map;
        }
        if(password.length()>=16){
            map.put("error_message", "密码长度不能超过16位");
            return map;
        }
        map.put("error_message", "register successfully!");
        Account account = new Account(accountId,password);
        accountMapper.insert(account);
        return map;
    }
}
