package com.oep.backend.serviceImpl.papertest;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.pojo.Testpaper;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTestPaperServiceImpl extends ClassTestPaper implements com.oep.backend.service.papertest.GetTestPaperService {
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Override
    public String getTestPaper(Integer currentPage) {
        Account account =  super.authenticate();
        Enterprise enterprise = super.getEnterpriseName(account);
        JSONObject jsonObject = new JSONObject();

        QueryWrapper<Testpaper> testpaperQueryWrapper = new QueryWrapper<>();
        testpaperQueryWrapper.orderByDesc("id");
        testpaperQueryWrapper.eq("enterprise_name", enterprise.getName());

        IPage<Testpaper> testpaperIPage = new Page<>(currentPage,7);
        testpaperIPage = testpaperMapper.selectPage(testpaperIPage,testpaperQueryWrapper);
        List<Testpaper> list = testpaperIPage.getRecords(); //  当前页数据

        jsonObject.put("testpaperList", list);
        jsonObject.put("dataCount",testpaperIPage.getTotal());
        jsonObject.put("current_page", testpaperIPage.getCurrent());
        jsonObject.put("sum_page_count", testpaperIPage.getPages());
        return WriteValue.writeValueAsString(jsonObject);
    }
}
