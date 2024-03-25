package com.oep.backend.serviceImpl.feedback;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.FeedbackMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Feedback;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.feedback.AddFeedbackService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddFeedbackServiceImpl implements AddFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public String AddFeedBack(Map<String, String> map) {
        String content = map.get("content");
        Map<String, String> respMap = new HashMap<>();
        if("".equals(content))  {
            respMap.put("error_message", "空值");
            return WriteValue.writeValueAsString(respMap);
        }
        Account account = getAccount();
        Feedback feedback = new Feedback(null, content, account.getAccountId());
        feedbackMapper.insert(feedback);

        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private Account getAccount()    {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
}
