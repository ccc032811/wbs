package com.neefull.fsp.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neefull.fsp.web.system.entity.ProjectEnrollment;
import com.neefull.fsp.web.system.mapper.ProjectEnrollmentMapper;
import com.neefull.fsp.web.system.service.IProjectEnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProjectEnrollmentServiceImpl extends ServiceImpl<ProjectEnrollmentMapper, ProjectEnrollment> implements IProjectEnrollmentService {
}
