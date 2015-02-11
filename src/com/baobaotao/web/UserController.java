package com.baobaotao.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}
