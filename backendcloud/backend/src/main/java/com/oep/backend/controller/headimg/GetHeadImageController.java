package com.oep.backend.controller.headimg;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetHeadImageController {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private CandidateMapper candidateMapper;
    @PostMapping("/candidate/get/headImg/")
    public String getHeadImg() throws IOException {
        Map<String, String> respMap = new HashMap<>();
        File file = new File(getImgPath());
        FileInputStream fis = null;
        try {
            byte[] bytes = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            int bytesRead = fis.read(bytes);// 返回读取的字节数,应该将其用作读取字节数据时的索引
            if (bytesRead != -1) {
                String base64String = Base64.getEncoder().encodeToString(bytes);
                respMap.put("base64String", base64String);
                respMap.put("error_message", "success");
            } else respMap.put("error_message", "文件读取失败");
        } catch (IOException e) {
            respMap.put("error_message", "文件读取失败");
        } finally {
            if(fis != null)    fis.close();
        }
        return WriteValue.writeValueAsString(respMap);
    }
    private String getImgPath() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        QueryWrapper<Candidate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        return candidateMapper.selectOne(queryWrapper).getHeadphoto();
    }
}
