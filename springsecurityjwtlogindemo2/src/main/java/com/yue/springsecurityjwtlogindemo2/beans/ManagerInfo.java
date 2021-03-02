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
@TableName("tab_manager_info")
@ApiModel(value="ManagerInfo对象", description="")
public class ManagerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "manager_info_id", type = IdType.AUTO)
    private Integer managerInfoId;

    private Integer managerAccountId;

    private String managerName;

    private String managerCode;

    private String managerEmail;


    public Integer getManagerInfoId() {
        return managerInfoId;
    }

    public void setManagerInfoId(Integer managerInfoId) {
        this.managerInfoId = managerInfoId;
    }

    public Integer getManagerAccountId() {
        return managerAccountId;
    }

    public void setManagerAccountId(Integer managerAccountId) {
        this.managerAccountId = managerAccountId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    @Override
    public String toString() {
        return "ManagerInfo{" +
        "managerInfoId=" + managerInfoId +
        ", managerAccountId=" + managerAccountId +
        ", managerName=" + managerName +
        ", managerCode=" + managerCode +
        ", managerEmail=" + managerEmail +
        "}";
    }
}
