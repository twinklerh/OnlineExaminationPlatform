package com.oep.backend.service.candidate_exam;

import java.util.Map;

public interface JoinExamService {
    String fillInviteCode(Map<String,String> map);
    String tryToJoinExam(Map<String, String> map);
}
