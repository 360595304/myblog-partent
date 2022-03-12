package com.hu.myblog.admin.service;

import com.hu.myblog.admin.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Service
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null) {
            return null;
        }
        return new User(username, admin.getPassword(), new ArrayList<>());
    }
}
