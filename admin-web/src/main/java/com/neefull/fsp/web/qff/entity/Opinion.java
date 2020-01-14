package com.neefull.fsp.web.qff.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 审批意见
 *
 * @Author: chengchengchu
 * @Date: 2019/11/27  16:06
 */

@TableName(value = "qff_opinion")
public class Opinion implements Serializable {

    private static final long serialVersionUID = -5438863982149794716L;
    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 关联id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 名称
     */
    @TableField("name")
    private String name;
    /**
     * 说明explain
     */
    @TableField("create_time")
    private Date createTime;
    /**
     *  状态码  0可用  1不可用
     */
    @TableField("update_time")
    private Date updateTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
