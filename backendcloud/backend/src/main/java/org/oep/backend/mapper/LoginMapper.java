package org.oep.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.oep.backend.pojo.Login;

@Mapper
public interface LoginMapper extends BaseMapper<Login> {
}
