package org.oep.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.oep.backend.pojo.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
