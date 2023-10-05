package com.fengxiangkai.service.impl;

import com.fengxiangkai.mapper.AdminMapper;
import com.fengxiangkai.pojo.Admin;
import com.fengxiangkai.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(Admin admin) {
        return adminMapper.getByUsernameAndPassword(admin);
    }
}
