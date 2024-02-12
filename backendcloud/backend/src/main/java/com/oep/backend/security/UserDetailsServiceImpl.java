package com.oep.backend.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.AccountMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.security.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String account_id) throws UsernameNotFoundException {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account_id);
        Account account = accountMapper.selectOne(queryWrapper);
        if (account == null) {
            throw new RuntimeException("用户不存在");
        }

        return new UserDetailsImpl(account);
    }
}
