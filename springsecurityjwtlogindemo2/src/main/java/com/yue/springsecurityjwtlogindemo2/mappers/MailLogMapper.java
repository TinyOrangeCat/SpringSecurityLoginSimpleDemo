package com.yue.springsecurityjwtlogindemo2.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yue.springsecurityjwtlogindemo2.beans.MailLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-03-02
 */
@Mapper
@Component("mailLogMapper")
public interface MailLogMapper extends BaseMapper<MailLog> {

}
