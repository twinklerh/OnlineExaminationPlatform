package com.oep.backend.serviceImpl.candidate_exam;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.CandidateProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.candidate_exam.FinishExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinishExamServiceImpl extends ClassCandidateExam implements FinishExamService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Autowired
    private CandidateProblemMapper candidateProblemMapper;
    private Candidate candidate;
    private float sumScore = 0;
    private Integer examId = 0;
    @Override
    public synchronized String submitExam(Map<String, String> map) {
        final Account account = super.getAccount();
        Map<String, String> respMap = new HashMap<>();
        if("candidate".equals(account.getStatus()))    candidate = super.getCandidate(account);
        else candidate = null;
        if(candidate == null) {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }

        System.out.println(map);
        String testpaperTitle = map.get("testpaperTitle");

        //拿到前端传过来的数据
        examId = Integer.valueOf(map.get("examId"));
        String s1 = map.get("myAnswer");
        String s2 = map.get("myAnswer_text");
        Map<String, Integer> myAnswer = null;
        Map<String, String> mytestAnswer = null;
        if(s1!=null && !s1.isEmpty())   {
            myAnswer = JSONObject.parseObject(map.get("myAnswer")).toJavaObject(Map.class);
        }
        if(s2!=null && !s2.isEmpty()) {
            mytestAnswer = JSONObject.parseObject(map.get("myAnswer_text")).toJavaObject(Map.class);
        }
        if (myAnswer != null)   caculate1(myAnswer);    //  计算选择题得分
        //  大题是人工改，要把它插入数据库
        System.out.println(this.sumScore);
        return null;
    }
    private void caculate1(Map<String, Integer> myAnswer) {
         //  选择题计分
        myAnswer.forEach((key, value)->{
            char ch = (char) (value + 65);
            String rightAnswer = problemMapper.selectById(key).getRightAnswer();
            System.out.println("正确答案：" + rightAnswer.charAt(0) + "，你的答案：" + ch);
            CandidateProblem candidateProblem = new CandidateProblem(null, Integer.valueOf(key), candidate.getCandidateId(), examId, "" + ch, new Date());
            candidateProblemMapper.insert(candidateProblem);
            if(ch == rightAnswer.charAt(0)) { //  答案正确，设置分值
                QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", Integer.valueOf(key));
                Problem problem = problemMapper.selectOne(queryWrapper);
                System.out.println(problem);
                Float score = problem.getScore();
                this.sumScore += score;
            }
        });
    }
}
