package com.oep.backend.service.account;

import java.util.Map;

public interface RegisterService {
    public Map<String,String> addAccount(String id, String password, String confirmPassword);
}
