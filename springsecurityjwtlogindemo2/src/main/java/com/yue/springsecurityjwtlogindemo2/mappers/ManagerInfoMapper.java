package com.yue.springsecurityjwtlogindemo2.mappers;

import com.yue.springsecurityjwtlogindemo2.beans.ManagerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
@Mapper
//@Component(value = "managerInfoMapper")
public interface ManagerInfoMapper extends BaseMapper<ManagerInfo> {

}
