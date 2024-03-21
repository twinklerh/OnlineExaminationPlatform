package com.oep.backend.serviceImpl.check;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.CandidateProblemMapper;
import com.oep.backend.pojo.CandidateProblem;
import com.oep.backend.service.check.SubmitCheckResultService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubmitCheckResultServiceImpl implements SubmitCheckResultService {
    @Autowired
    private CandidateProblemMapper candidateProblemMapper;
    @Override
    public String submitCheckResult(Map<String, String> map) {
        Map<String, String> respMap = new HashMap<>();
        map.forEach((key, value)-> {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(key);
            if(matcher.find())  {
                if(!updateCandidateProblem(Integer.valueOf(matcher.group()), Float.valueOf(value))) {
                    respMap.put("error_message", "error");
                }
            }
        });
        if("error".equals(respMap.get("error_message")))    return WriteValue.writeValueAsString(respMap);
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private boolean updateCandidateProblem(Integer checkId, Float score)    {
        UpdateWrapper<CandidateProblem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", checkId);
        updateWrapper.set("get_score", score);
        updateWrapper.set("is_checked", true);
        candidateProblemMapper.update(updateWrapper);
        return true;
    }
}