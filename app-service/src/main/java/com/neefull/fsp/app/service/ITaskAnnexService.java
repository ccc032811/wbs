package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.TaskAnnex;

import java.util.List;

/**
 * @author pei.wang
 */
public interface ITaskAnnexService extends IService<TaskAnnex> {

    /**
     * 保存用户完成任务附件
     *
     * @param annex
     * @return
     */
    boolean saveTaskAnnexBatch(List<TaskAnnex> annex);

    /**
     * 查询用户完成任务附件信息
     *
     * @param annex
     * @return
     */
    List<TaskAnnex> queryTaskAnnex(TaskAnnex annex);
}
