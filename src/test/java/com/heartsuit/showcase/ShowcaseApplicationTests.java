package com.heartsuit.showcase;

import com.heartsuit.showcase.service.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowcaseApplicationTests {

	@Autowired
	private IMailService mailService;

	@Test
	public void sendmailHtml(){
		mailService.sendHtmlMail("791917934@qq.com","主题：你好html邮件","<h1>内容：第一封html邮件</h1>");
	}

}
