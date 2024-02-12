package com.oep.backend.security.utils;

import com.oep.backend.pojo.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Account account;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getAccountId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {   //  账号是否没有被锁
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //  授权是否过期
        return true;
    }

    @Override
    public boolean isEnabled() {    //  用户是否被启用
        return true;
    }
}
