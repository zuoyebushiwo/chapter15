package com.baobaotao.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.baobaotao.UserService;
import com.baobaotao.domain.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 处理/user的请求，不过请求的方法必须为POST
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createUser(User user) {
		userService.createUser(user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/createSuccess");
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping("/register")
	public String register() {
		return "user/register";
	}

	// @RequestMapping("/{userId}")
	// public ModelAndView showDetail(@PathVariable("userId") String userId) {
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("user/showUser");
	// mav.addObject("user", userService.getUserById(userId));
	// return mav;
	// }

	/**
	 * 使用请求方法及请求参数映射请求
	 * 
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, params = "userId")
	public String test1() {
		return "user/test1";
	}

	/**
	 * 使用报文头映射请求
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/show", headers = "content-type=text/*")
	public String test2(String userId) {
		return "user/test2";
	}

	/**
	 * ①.请求参数按名称匹配的方式绑定到方法入参中，方法返回对应的字符串代表逻辑视图名
	 * 
	 * @param userName
	 * @param passWord
	 * @param realName
	 * @return
	 */
	@RequestMapping(value = "/handle1")
	public String handle1(@RequestParam("userName") String userName,
			@RequestParam("password") String passWord,
			@RequestParam("realName") String realName) {
		return "success";
	}

	/**
	 * ②.将Cookie值及报文头属性绑定到入参中，方法返回ModelAndView
	 * 
	 * @param sessionId
	 * @param acceptLanguage
	 * @return
	 */
	@RequestMapping(value = "handle2")
	public ModelAndView handle2(@CookieValue("JSESSIONID") String sessionId,
			@RequestHeader("Accept-Language") String acceptLanguage) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		mv.addObject("user", new User());
		return mv;
	}

	// ③请求参数按名称匹配的方式绑定到user的属性中、方法返回对应的字符串代表逻辑视图名
	@RequestMapping(value = "/handle3")
	public String handle3(User user) {
		return "success";
	}

	// ④直接将HTTP请求对象传递给处理方法、方法返回对应的字符串代表逻辑视图名
	@RequestMapping(value = "/handle4")
	public String handle4(HttpServletRequest request) {
		return "success";
	}

	@RequestMapping(value = "/handle11")
	public String handle11(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam("age") int age) {
		return "success";
	}

	@RequestMapping(value = "/handle12")
	public String handle12(
			@CookieValue(value = "sessionId", required = false) String sessionId,
			@RequestParam("age") int age) {
		return "success";
	}

	@RequestMapping(value = "/handle13")
	public String handle13(@RequestHeader("Accept-Encoding") String encoding,
			@RequestHeader("Keep-Alive") long keepAlive) {
		return "success";
	}

	@RequestMapping(value = "/handle14")
	public String handle14(User user) {
		return "success";
	}

	@RequestMapping(value = "/handle21")
	public void handle21(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter("userName");
		response.addCookie(new Cookie("userName", userName));
	}

	@RequestMapping(value = "/handle22")
	public ModelAndView handle22(HttpServletRequest request) {
		String userName = WebUtils.findParameterValue(request, "userName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("success");
		mav.addObject("userName", userName);
		return mav;
	}

	@RequestMapping(value = "/handle23")
	public String handle23(HttpSession session) {
		session.setAttribute("sessionId", 1234);
		return "success";
	}

	@RequestMapping(value = "/success")
	public String success() {
		return "success";
	}

	@RequestMapping(value = "/fail")
	public String fail() {
		return "fail";
	}

	@RequestMapping(value = "/handle24")
	public String handle24(HttpServletRequest request,
			@RequestParam("userName") String userName) {

		return "success";
	}

	@RequestMapping(value = "/handle25")
	public String handle25(WebRequest request) {
		String userName = request.getParameter("userName");
		return "success";
	}

	@RequestMapping(value = "/handle31")
	public void handle31(OutputStream os) throws IOException {
		Resource res = new ClassPathResource("/image.jpg");
		FileCopyUtils.copy(res.getInputStream(), os);
	}
	
	/**
	 * 将请求报文体转化为字符串绑定到requestBody入参中
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/handle41")
	public String handle41(@RequestBody String body) {
		System.out.println(body);
		return "success";
	}
	
	/**
	 * 读取一张图片，并将图片数据输出到响应流中，客户端将显示这张图片
	 * 
	 * @param imageId
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/handle42/{imageId}")
	public byte[] handle42(String imageId) throws IOException {
		System.out.println("load image of " + imageId);
		Resource res = new ClassPathResource("/image.jpg");
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		return fileData;
	}

}
