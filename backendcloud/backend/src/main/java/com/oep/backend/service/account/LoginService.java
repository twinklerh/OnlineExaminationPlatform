package com.oep.backend.service.account;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String account_id, String password);
}
