package com.oep.backend.serviceImpl.submitProblem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.GroupMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.pojo.Group;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.submitProblem.GetAllGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetAllGroupServiceImpl implements GetAllGroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public Map<String,List<String>> getAllGroup() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();

        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id",account.getAccountId());
        Enterprise enterprise = enterpriseMapper.selectOne(enterpriseQueryWrapper);

        System.out.println(enterprise.getEnterpriseId());

        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterprise_id",enterprise.getEnterpriseId());
        List<String> stringList = groupMapper.selectList(queryWrapper).stream()
                .map(Group::getGroupName).collect(Collectors.toList());
        // 首先调用了 stream() 方法将 List<Group> 转换为 Stream
        // 然后使用 map() 方法将每个 Group 对象映射为 Group 对象 的 GroupName 属性的值
        // 最后使用 collect() 方法将映射后的结果收集到一个新的 List<String> 中
        Map<String,List<String>> returnMap = new HashMap<>();
        returnMap.put("stringList", stringList);
        return returnMap;
    }
}
