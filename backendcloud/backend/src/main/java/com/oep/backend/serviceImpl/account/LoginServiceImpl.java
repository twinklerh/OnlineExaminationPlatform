package com.oep.backend.serviceImpl.account;

import com.oep.backend.pojo.Account;
import com.oep.backend.security.JwtUtil;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Map<String, String> getToken(String account_id, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account_id, password);
        Map<String, String> map = new HashMap<>();
        try{
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);  //  如果登录失败会自动处理
            UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
            Account account = loginUser.getAccount();
            String jwt = JwtUtil.createJWT(account.getAccountId());
            map.put("error_message","success");
            map.put("token", jwt);
        } catch(AuthenticationException e){
            map.put("error_message", "Authentication failed");
        }
        return map;
    }
}
