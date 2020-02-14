package com.neefull.fsp.web.generator.servie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neefull.fsp.web.generator.entity.GeneratorConfig;

/**
 * @author pei.wang
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
