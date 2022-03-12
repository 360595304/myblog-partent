package com.hu.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.admin.entity.Article;
import com.hu.myblog.admin.entity.SysUser;
import com.hu.myblog.admin.mapper.SysUserMapper;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.service.ArticleService;
import com.hu.myblog.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ArticleService articleService;


    @Override
    public SysUser findUser(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "account", "avatar", "nickname", "mobile_phone_number", "email",
                        "create_date", "last_login")
                .eq("account", username)
                .eq("password", password);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }


    @Override
    public SysUser findUserByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", username).last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<SysUser> getSysUserPage(PageParam pageParam) {
        Page<SysUser> userPage = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        String queryString = pageParam.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            queryWrapper
                    .like(SysUser::getNickname, queryString)
                    .or()
                    .like(SysUser::getAccount, queryString);
        }
        Page<SysUser> page = userMapper.selectPage(userPage, queryWrapper);
        this.packUser(page.getRecords());
        return page;
    }

    private void packUser(List<SysUser> records) {
        records.forEach(user->{
            List<Article> articleList = articleService.findArticleByUserId(user.getId());
            int count = 0;
            if (articleList != null) {
                for (Article article : articleList) {
                    if (article.getPublished() == 1) ++count;
                }
            }
            user.getParams().put("articleCount", count);
        });
    }
}
