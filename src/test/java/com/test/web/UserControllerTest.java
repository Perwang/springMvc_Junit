package com.test.web;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.test.domain.User;
import com.test.util.JsonUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations="classpath:spring-config-mvc.xml")
public class UserControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void printBeans(){
		String[] beans=webApplicationContext.getBeanDefinitionNames();
		for (String bean : beans) {
			System.out.println(bean);
		}
	}
	/*测试以普通请求参数发送的请求*/
	@Test
	public void testSave() throws Exception {
		ResultActions result=mockMvc.perform(post("/user/save").param("id", "123").param("username", "you")).andExpect(status().isOk()).andExpect(view().name("result")).andDo(MockMvcResultHandlers.print());
		System.out.println(result);
	}
	/*测试将数据以JSON格式写入请求体发送的请求*/
	@Test
	public void testGet() throws Exception {
		ResultActions result=mockMvc.perform(post("/user/getUser").contentType(JsonUtil.APPLICATION_JSON_UTF8).content(JsonUtil.convertObjectToJsonBytes(new User(22,"王灿佩")))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
		System.out.println(result);
	}
	/*测试将数据以JSON格式写入请求体发送的请求*/
	@Test
	public void testGetAll() throws IOException, Exception {
		List<User> list=new ArrayList<User>();
		list.add(new User(23,"你爱我"));
		list.add(new User(25,"我不爱你"));
		mockMvc.perform(post("/user/getUsers").contentType(JsonUtil.APPLICATION_JSON_UTF8).content(JsonUtil.convertObjectToJsonBytes(list))).andExpect(status().isOk());
	}
	/*测试文件上传发送的请求*/
	@Test
	public void testUpload() throws Exception{
		MockMultipartFile file = new MockMultipartFile("file", "orig.txt", null, "bar".getBytes());
		mockMvc.perform(fileUpload("/user/upload").file(file)).andExpect(status().isOk());
	}
}
