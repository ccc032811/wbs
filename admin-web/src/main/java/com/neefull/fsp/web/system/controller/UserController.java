package com.neefull.fsp.web.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.neefull.fsp.common.util.EncryptUtil;
import com.neefull.fsp.web.common.annotation.Log;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsConstant;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.entity.QueryRequest;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.system.entity.TemplateCorp;
import com.neefull.fsp.web.system.entity.TemplateLancer;
import com.neefull.fsp.web.system.entity.User;
import com.neefull.fsp.web.system.service.IUserService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author pei.wang
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("{username}")
    public User getUser(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findUserDetail(username);
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username, String userId) {
        return this.userService.findByName(username) == null || StringUtils.isNotBlank(userId);
    }

    @GetMapping("list")
    @RequiresPermissions("user:view")
    public FebsResponse userList(User user, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.userService.findUserDetail(user, request));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("userLst")
    public FebsResponse userLst(User user, QueryRequest request) {
        return new FebsResponse().success().data(this.userService.getAllUseUserLst());
    }

    @Log("新增用户")
    @PostMapping
    @RequiresPermissions("user:add")
    public FebsResponse addUser(@Valid User user) throws FebsException {
        try {
            this.userService.createUser(user);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "新增用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除用户")
    @GetMapping("delete/{userIds}")
    @RequiresPermissions("user:delete")
    public FebsResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "删除用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改用户")
    @PostMapping("update")
    @RequiresPermissions("user:update")
    public FebsResponse updateUser(@Valid User user) throws FebsException {
        try {
            if (user.getUserId() == null)
                throw new FebsException("用户ID为空");
            this.userService.updateUser(user);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改用户失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("审核用户")
    @GetMapping("examine/{userIds}")
    @RequiresPermissions("user:examine")
    public FebsResponse examineUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.examineUsers(ids);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "操作失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PostMapping("password/reset/{usernames}")
    @RequiresPermissions("user:password:reset")
    public FebsResponse resetPassword(@NotBlank(message = "{required}") @PathVariable String usernames) throws FebsException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "重置用户密码失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("password/update")
    public FebsResponse updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String newPassword) throws FebsException {
        try {
            User user = getCurrentUser();
            if (!StringUtils.equals(user.getPassword(), EncryptUtil.encrypt(oldPassword, FebsConstant.AES_KEY))) {
                throw new FebsException("原密码不正确");
            }
            userService.updatePassword(user.getUsername(), newPassword);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改密码失败，" + e.getMessage();
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("avatar/{image}")
    public FebsResponse updateAvatar(@NotBlank(message = "{required}") @PathVariable String image) throws FebsException {
        try {
            User user = getCurrentUser();
            this.userService.updateAvatar(user.getUsername(), image);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改头像失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("theme/update")
    public FebsResponse updateTheme(String theme, String isTab) throws FebsException {
        try {
            User user = getCurrentUser();
            this.userService.updateTheme(user.getUsername(), theme, isTab);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改系统配置失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("profile/update")
    public FebsResponse updateProfile(User user) throws FebsException {
        try {
            User currentUser = getCurrentUser();
            user.setUserId(currentUser.getUserId());
            this.userService.updateProfile(user);
            return new FebsResponse().success();
        } catch (Exception e) {
            String message = "修改个人信息失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("excel")
    @RequiresPermissions("user:export")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws FebsException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("inportLancer")
    @RequiresPermissions("user:inport")
    public FebsResponse inportLancer(MultipartFile file) throws FebsException {
        try {
            if (file.isEmpty()) {
                throw new FebsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new FebsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            Stopwatch stopwatch = Stopwatch.createStarted();
            final List<TemplateLancer> data = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(TemplateLancer.class).readXlsx(file.getInputStream(), new ExcelReadHandler<TemplateLancer>() {

                @Override
                public void onSuccess(int i, int i1, TemplateLancer templateLancer) {
                    // 数据校验成功时，加入集合
                    data.add(templateLancer);
                }

                @Override
                public void onError(int i, int i1, List<ExcelErrorField> list) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", i1, "errorFields", list));
                }
            });
            if (CollectionUtils.isNotEmpty(data)) {
                // 将合法的记录批量入库
                this.userService.batchLancerInsert(data);
            }
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", stopwatch.stop().toString(),
                    "data", data,
                    "error", error
            );
            return new FebsResponse().success().data(result);
        } catch (Exception e) {
            String message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }
    }


    @PostMapping("inportCorp")
    @RequiresPermissions("user:inport")
    public FebsResponse inportCorp(MultipartFile file) throws FebsException {
        try {
            if (file.isEmpty()) {
                throw new FebsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new FebsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            Stopwatch stopwatch = Stopwatch.createStarted();
            final List<TemplateCorp> data = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(TemplateCorp.class).readXlsx(file.getInputStream(), new ExcelReadHandler<TemplateCorp>() {

                @Override
                public void onSuccess(int i, int i1, TemplateCorp templateCorp) {
                    // 数据校验成功时，加入集合
                    data.add(templateCorp);
                }

                @Override
                public void onError(int i, int i1, List<ExcelErrorField> list) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", i1, "errorFields", list));
                }
            });
            if (CollectionUtils.isNotEmpty(data)) {
                // 将合法的记录批量入库
                this.userService.batchCorpInsert(data);
            }
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", stopwatch.stop().toString(),
                    "data", data,
                    "error", error
            );
            return new FebsResponse().success().data(result);
        } catch (Exception e) {
            String message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }
    }

    @GetMapping("templateLancer")
    public void generateTemplateLancer(HttpServletResponse response) {
        // 构建数据
        List<TemplateLancer> list = new ArrayList<>();
        TemplateLancer eximport = new TemplateLancer();
        eximport.setField1("1");
        eximport.setField2("yunxing");
        eximport.setField3("13636311528@163.com");
        eximport.setField4("13636311528");
        eximport.setField5("310000000000000001");
        eximport.setField6("运行");
        eximport.setField7("6225768787560778654");
        eximport.setField8("招商银行");
        eximport.setField9("上海市");
        eximport.setField10("银行简称");
        eximport.setField11("招商银行田林支行");
        list.add(eximport);
        eximport = new TemplateLancer();
        eximport.setField1("1");
        eximport.setField2("keyi");
        eximport.setField3("13636311527@163.com");
        eximport.setField4("13636311527");
        eximport.setField5("310000000000000002");
        eximport.setField6("可以");
        eximport.setField7("6225768787560778653");
        eximport.setField8("中国银行");
        eximport.setField9("合肥市");
        eximport.setField10("银行简称");
        eximport.setField11("中国银行合肥支行");
        list.add(eximport);
        // 构建模板
        ExcelKit.$Export(TemplateLancer.class, response).downXlsx(list, true);
    }

    @GetMapping("templateCorp")
    public void generateTemplateCorp(HttpServletResponse response) {
        // 构建数据
        List<TemplateCorp> list = new ArrayList<>();
        TemplateCorp eximport = new TemplateCorp();
        for(int i=0;i<2;i++){
            eximport.setField1("0");
            eximport.setField2("keyi");
            eximport.setField3("13636311527@163.com");
            eximport.setField4("13636311527");
            eximport.setField5("张三");
            eximport.setField6("上海市民有限公司");
            eximport.setField7("32823743878923782");
            eximport.setField8("3940393929389242394");
            eximport.setField9("中国银行徐汇支行");
            eximport.setField10("上海市徐汇区田林街道数据路29号");
            eximport.setField11("021-03948443");
            eximport.setField12("34234212321");
            list.add(eximport);
        }
        // 构建模板
        ExcelKit.$Export(TemplateCorp.class, response).downXlsx(list, true);
    }
}
