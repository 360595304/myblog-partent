package com.hu.myblog.admin.service;

import com.hu.myblog.admin.entity.Admin;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Service
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication) {
        String uri = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        if (principal==null || "anonymousUser".equals(principal)) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null) return false;
        Long id = admin.getId();
        if (id == 1) return true; //超级管理员直接放行
        uri = StringUtils.split(uri, "?")[0];
        List<Role> roleList = adminService.findRoleByAdminId(id);
        for (Role role : roleList) {
            List<Permission> permissionList = adminService.findPermissionByRoleId(role.getId());
            for (Permission permission : permissionList) {
                if (uri.equals(permission.getPath())) return true;
            }
        }
        return false;
    }
}
