package com.oep.backend.service.account;

import java.util.Map;

public interface LoginService {
    public String getToken(String account_id, String password);
}
