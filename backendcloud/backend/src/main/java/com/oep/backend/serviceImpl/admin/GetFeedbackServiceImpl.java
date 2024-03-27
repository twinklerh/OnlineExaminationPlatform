package com.oep.backend.serviceImpl.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oep.backend.mapper.FeedbackMapper;
import com.oep.backend.pojo.Feedback;
import com.oep.backend.service.admin.GetFeedbackService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetFeedbackServiceImpl implements GetFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public String getFeedbackInfo(int current_page) {
        IPage<Feedback> iPage = new Page<>(current_page, 10);
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("feedback_id");
        iPage = feedbackMapper.selectPage(iPage, queryWrapper);
        List<Feedback> list = iPage.getRecords();

        Map<String, String> respMap = new HashMap<>();
        respMap.put("error_message", "success");
        respMap.put("total", String.valueOf(iPage.getTotal()));
        respMap.put("feedbackList", WriteValue.writeValueAsString(list));
        return WriteValue.writeValueAsString(respMap);
    }
}
