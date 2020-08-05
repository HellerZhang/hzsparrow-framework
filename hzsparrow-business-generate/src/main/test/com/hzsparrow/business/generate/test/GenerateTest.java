package com.hzsparrow.business.generate.test;


import com.hzsparrow.business.generate.entity.GenConfig;
import com.hzsparrow.business.generate.service.GenerateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateTest {
    @Autowired
    private GenerateService generateService;

    @Test
    public void generate() {
        GenConfig config = new GenConfig();
        config.setPackageName("com.hzsparrow.business.generate");
        config.setAuthor("HellerZhang");
        generateService.generateCode("hzs_user", config);
    }

}
