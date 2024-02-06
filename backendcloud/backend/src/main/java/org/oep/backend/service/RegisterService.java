package org.oep.backend.service;

import java.util.Map;

public interface RegisterService {
    public Map<String,String> addAccount(String id, String password, String confirmPassword);
}
