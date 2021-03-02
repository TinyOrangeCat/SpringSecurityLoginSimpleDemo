package com.yue.springsecurityjwtlogindemo2.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
@TableName("tab_manager")
@ApiModel(value="Manager对象", description="")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "manager_id", type = IdType.AUTO)
    private Integer managerId;

    private String managerAccount;

    private String managerPassword;

    private Integer managerRoleId;


    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerAccount() {
        return managerAccount;
    }

    public void setManagerAccount(String managerAccount) {
        this.managerAccount = managerAccount;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public Integer getManagerRoleId() {
        return managerRoleId;
    }

    public void setManagerRoleId(Integer managerRoleId) {
        this.managerRoleId = managerRoleId;
    }

    @Override
    public String toString() {
        return "Manager{" +
        "managerId=" + managerId +
        ", managerAccount=" + managerAccount +
        ", managerPassword=" + managerPassword +
        ", managerRoleId=" + managerRoleId +
        "}";
    }
}
