package com.neefull.fsp.web.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.neefull.fsp.web.common.annotation.IsCron;
import com.neefull.fsp.web.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author pei.wang
 */
@Data
@TableName("job")
public class AdminJob implements Serializable {

    private static final long serialVersionUID = 400066840871805700L;

    /**
     * 任务调度参数 key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @TableId(value = "JOB_ID", type = IdType.AUTO)
    private Long jobId;

    @TableField("name")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "服务名称")
    private String name;

    @TableField("bean_name")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "Bean名称")
    private String beanName;

    @TableField("method_name")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "方法名称")
    private String methodName;

    @TableField("params")
    @NotBlank(message = "{required}")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "IP")
    private String params;

    @TableField("cron_expression")
//    @IsCron(message = "{invalid}")
    @ExcelField(value = "Cron表达式")
    private String cronExpression;

    @TableField("status")
    @ExcelField(value = "任务状态", writeConverterExp = "0=正常,1=暂停")
    private String status;

    @TableField("isalive")
    @ExcelField(value = "服务状态", writeConverterExp = "0=正常,1=下线")
    private String isalive;

    @TableField("remark")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "备注")
    private String remark;

    @TableField("create_time")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;

}
