package com.oep.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.oep.backend.pojo.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
