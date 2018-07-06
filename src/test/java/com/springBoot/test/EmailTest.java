package com.springBoot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seeMovie.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class EmailTest {
	 @Autowired
	    private JavaMailSender javaMailSender;
	    @Test
	    public void testSend(){
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("tianshouzhi@126.com");//发送者.
	        message.setTo("792300609@qq.com");//接收者.
	        message.setSubject("测试邮件（邮件主题）");//邮件主题.
	        message.setText("这是邮件内容");//邮件内容.
	        javaMailSender.send(message);//发送邮件
	    }
}
