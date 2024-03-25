package com.oep.backend.controller.headimg;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UpdateHeadImageController {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private CandidateMapper candidateMapper;
    @PostMapping("/candidate/upload/headImage/")
    public String uploadHeadImage(MultipartFile file) throws IOException {
        Candidate candidate = getCandidate();
        byte[] bytes = new byte[20];
        Map<String, String> respMap = new HashMap<>();
        String path = getRootDirectory() + "\\headerImages\\";

        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        try{
            File folder = new File(path);
            if(!folder.exists()) {
                boolean created = folder.mkdirs();
                if (!created) System.out.println("文件夹创建失败！");
            }
            fileOutputStream = new FileOutputStream(path + uuid+".png");
            fileInputStream = (FileInputStream) file.getInputStream();
            int read = fileInputStream.read(bytes);
            while(read != -1) {
                fileOutputStream.write(bytes, 0, bytes.length);
                read = fileInputStream.read(bytes);
            }
        }   catch(Exception e)  {
            respMap.put("error_message", "文件写入异常");
            return WriteValue.writeValueAsString(respMap);
        } finally {
            if (fileInputStream != null)    fileInputStream.close();
            if (fileOutputStream != null)   fileOutputStream.close();
        }

        UpdateWrapper<Candidate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("candidate_id", candidate.getCandidateId());
        updateWrapper.set("headphoto", path + uuid+".png");
        candidateMapper.update(updateWrapper);

        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    public String getRootDirectory() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:");
        return resource.getFile().getAbsolutePath();
    }
    private Candidate getCandidate()    {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        QueryWrapper<Candidate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        return candidateMapper.selectOne(queryWrapper);
    }
}
