package com.yue.springsecurityjwtlogindemo2.services;

import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
public interface IManagerService extends IService<Manager> {

    Manager loadManagerByName(String name);

    List<Manager> getAllManagersByKeyWords(String keyWords);

}
