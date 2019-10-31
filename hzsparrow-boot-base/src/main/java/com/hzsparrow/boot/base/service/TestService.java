package com.hzsparrow.boot.base.service;

import com.hzsparrow.boot.base.entity.Test;
import com.hzsparrow.boot.base.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public Test getById(Integer id){
        return testMapper.selectByPrimaryKey(id);
    }
}
