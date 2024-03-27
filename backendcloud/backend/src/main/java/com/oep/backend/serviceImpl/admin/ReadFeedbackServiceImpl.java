package com.oep.backend.serviceImpl.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.FeedbackMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Feedback;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.admin.ReadFeedbackService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReadFeedbackServiceImpl implements ReadFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public String readFeedback(int feedbackId) {
        Map<String, String> respMap = new HashMap<>();
        if(!auth())  {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        UpdateWrapper<Feedback> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("feedback_id", feedbackId);
        updateWrapper.set("is_read", true);
        feedbackMapper.update(updateWrapper);
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private boolean auth()  {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        return "root".equals(account.getStatus());
    }
}
