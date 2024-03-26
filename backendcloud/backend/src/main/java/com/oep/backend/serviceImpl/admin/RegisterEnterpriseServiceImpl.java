package com.oep.backend.serviceImpl.admin;

import com.oep.backend.mapper.AccountMapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.admin.RegisterEnterpriseService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterEnterpriseServiceImpl implements RegisterEnterpriseService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public String registerEnterpriseAccount(Map<String, String> map) {
        Account account = auth();
        Map<String, String> respMap = new HashMap<>();
        if(!"root".equals(account.getStatus()))  {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }

        String email = map.get("email");
        String password = map.get("password");
        String account_id = map.get("username");
        String enterpriseName = map.get("enterprise_name");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);

        Account enterpriseAccount = new Account(account_id, encodePassword, "enterprise");
        accountMapper.insert(enterpriseAccount);

        Enterprise enterprise = new Enterprise(null, enterpriseName, email, enterpriseAccount.getAccountId());
        enterpriseMapper.insert(enterprise);

        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private Account auth()  {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
}
