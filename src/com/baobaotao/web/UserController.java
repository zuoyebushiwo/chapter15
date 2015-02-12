package com.baobaotao.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	/**
	 * ③.请求参数按名称匹配的方式绑定到user属性中、方法返回对应的
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/handle3")
	public String handle3(User user) {
		return "success";
	}

}
