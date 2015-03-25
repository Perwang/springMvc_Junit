package com.test.web;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.domain.User;
import com.test.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController{
	private static final String FILES_DIR="e:/";
	@Resource
	private UserService userService;
	
	/**
	 * 这个用来测试mode类型的，就是
	 * @param user
	 * @return
	 */
	@RequestMapping("/save")
	public String save(User user) {
		userService.save(user);
		return "result";
	}
	@RequestMapping("/getUser")
	public @ResponseBody User getUser(HttpServletRequest request,@RequestBody User user) {
		System.out.println("AAAAAAA::"+user);
		request.setAttribute("data1",user);
		return user;
	}
	@RequestMapping("/getUsers")
	public @ResponseBody List<User> getUsers(HttpServletRequest request,@RequestBody List<User> users) {
		System.out.println(users.getClass().getName());
		for (User user : users) {
			System.out.println("BBBBBBBB::"+user);
		}
		request.setAttribute("data2", users);
		return users;
	}
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String add(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
		//System.out.println("real path::"+request.getServletContext().getRealPath("/"));
		String filename=file.getOriginalFilename();
		File tmpFile=new File(FILES_DIR+filename);
		if(filename!=null&&!file.isEmpty()){
			try {
				FileCopyUtils.copy(file.getBytes(), tmpFile);
				System.out.println("上传成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "result";
	}
	
}
