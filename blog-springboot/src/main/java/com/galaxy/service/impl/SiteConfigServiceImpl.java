package com.galaxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.galaxy.entity.SiteConfig;
import com.galaxy.mapper.SiteConfigMapper;
import com.galaxy.service.RedisService;
import com.galaxy.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.galaxy.constant.RedisConstant.SITE_SETTING;

/**
 * 网站配置业务接口实现类
 *
 * @author galaxy
 */
@Service
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService {

    @Autowired
    private SiteConfigMapper siteConfigMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public SiteConfig getSiteConfig() {
        SiteConfig siteConfig = redisService.getObject(SITE_SETTING);
        if (Objects.isNull(siteConfig)) {
            // 从数据库中加载
            siteConfig = siteConfigMapper.selectById(1);
            redisService.setObject(SITE_SETTING, siteConfig);
        }
        return siteConfig;
    }

    @Override
    public void updateSiteConfig(SiteConfig siteConfig) {
        baseMapper.updateById(siteConfig);
        redisService.deleteObject(SITE_SETTING);
    }
}




