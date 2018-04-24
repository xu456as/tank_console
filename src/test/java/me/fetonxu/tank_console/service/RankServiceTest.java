package me.fetonxu.tank_console.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RankServiceTest {

    @Autowired
    private RankService rankService;

    @Test
    public void test(){
        Assert.assertTrue("Autowire fail", rankService != null);
    }

}
