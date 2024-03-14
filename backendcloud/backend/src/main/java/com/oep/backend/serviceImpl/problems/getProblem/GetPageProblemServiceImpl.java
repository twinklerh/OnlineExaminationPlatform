package com.oep.backend.serviceImpl.problems.getProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oep.backend.mapper.GroupProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.problems.getProblem.GetPageProblemService;
import com.oep.backend.serviceImpl.problems.ClassProblem;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetPageProblemServiceImpl extends ClassProblem implements GetPageProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private GroupProblemMapper groupProblemMapper;
    @Override
    public String getPageProblems(int current_page) {
        int enterpriseId = super.authenticateGetEnterpriseId();
        Map<String, String> respMap = new HashMap<>();
        IPage<Problem> iPage = new Page<>(current_page,8);
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("enterprise_id", enterpriseId);
        iPage = problemMapper.selectPage(iPage, problemQueryWrapper);
        if(iPage.getRecords().isEmpty())  {
            respMap.put("error_message", "数据为空");
            return WriteValue.writeValueAsString(respMap);
        }
        List<Problem> problemList = iPage.getRecords();
        int sum_page = (int) iPage.getTotal();
        respMap.put("problemList", WriteValue.writeValueAsString(problemList));
        respMap.put("error_message", "success");
        respMap.put("sum_page", String.valueOf(sum_page));
        return WriteValue.writeValueAsString(respMap);
    }
}
