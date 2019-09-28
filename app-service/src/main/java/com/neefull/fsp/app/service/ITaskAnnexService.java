package com.neefull.fsp.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.app.entity.TaskAnnex;
import com.neefull.fsp.app.entity.TaskAnnexDetail;

import java.util.List;

/**
 * @author pei.wang
 */
public interface ITaskAnnexService extends IService<TaskAnnexDetail> {

    /**
     * 保存用户完成任务附件
     *
     * @param taskAnnex
     * @return
     */
    int completedTask(TaskAnnex taskAnnex);

    /**
     * 查询用户完成任务附件信息
     *
     * @param annex
     * @return
     */
    TaskAnnex queryTaskAnnex(TaskAnnex annex);
}
