package com.oep.backend.serviceImpl.exams;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oep.backend.mapper.CandidateExamMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.CandidateExam;
import com.oep.backend.pojo.Exam;
import com.oep.backend.service.exams.GetMyExamService;
import com.oep.backend.serviceImpl.candidate_exam.ClassCandidateExam;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetMyExamServiceImpl extends ClassCandidateExam implements GetMyExamService {
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    @Autowired
    private ExamMapper examMapper;
    @Override
    public String getMyExam(Integer currentPage) {
        Map<String, String> respMap = new HashMap<>();
        Account account = super.getAccount();
        if(!"candidate".equals(account.getStatus())) {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        Integer candidateId = super.getCandidate(account).getCandidateId();
        List<Integer> examIdList = getMyExamIdList(candidateId);

        if(examIdList.isEmpty())    {
            respMap.put("error_message", "数据为空");
            return WriteValue.writeValueAsString(respMap);
        }

        IPage<Exam> iPage = new Page<>(currentPage, 6);
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("exam_id",examIdList);
        queryWrapper.orderByDesc("begin_time");
        iPage = examMapper.selectPage(iPage, queryWrapper);
        List<Exam> myExamList = iPage.getRecords();
        respMap.put("dataCount", String.valueOf(iPage.getTotal()));
        respMap.put("myExamList", WriteValue.writeValueAsString(myExamList));
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private List<Integer> getMyExamIdList(Integer candidateId) {
        QueryWrapper<CandidateExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("candidate_id", candidateId);
        return candidateExamMapper.selectList(queryWrapper)
                .stream().map(CandidateExam::getExamId).toList();
    }
}
