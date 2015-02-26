package com.baobaotao.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.baobaotao.UserService;
import com.baobaotao.domain.Dept;
import com.baobaotao.domain.User;
import com.baobaotao.domain.UserEditor;

@Controller
@RequestMapping("/user")
@SessionAttributes("user") // 将模型属性自动保存到HttpSession中
public class UserController {

	@Autowired
	private UserService userService;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@NumberFormat(pattern = "#,###.##")
	private long salay;
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public long getSalay() {
		return salay;
	}

	public void setSalay(long salay) {
		this.salay = salay;
	}

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
	
	/**
	 * 和@RequestBody/@ResponseBody类似，HttpEntity<?>不但可以访问请求及响应报文的数据，
	 * 还可以访问请求和响应报文头的数据。Spring MVC根据HttpEntity的泛型类型查找对应的HttpMessageConverter。
	 * 
	 * 使用StringHttpMessageConverter将请求报文体及报文头的信息绑定到httpEntity中，在方法中可以对相应信息进行访问
	 * 
	 * @param httpEntity
	 * @return
	 */
	@RequestMapping(value = "/handle43")
	public String handle43(HttpEntity<String> httpEntity) {
		long contentLen = httpEntity.getHeaders().getContentLength();
		System.out.println(httpEntity.getBody());
		return "success";
	}
	
	/**
	 * 在方法中创建HttpEntity<btye[]>对象并返回，ByteArrayHttpMessageConverter负责其写出到响应流中
	 * 
	 * @param imageId
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/handle44/{imageId}")
	public ResponseEntity<byte[]> handle44(
			@PathVariable("imageId") String imageId) throws Throwable {
		Resource res = new ClassPathResource("/image.jpg");
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(fileData, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 支持XML和JSON消息的处理方法
	 * 
	 * @param requestEntity
	 * @return
	 */
	@RequestMapping(value = "/handle51")
	public ResponseEntity<User> handle51(HttpEntity<User> requestEntity) {
		User user = requestEntity.getBody();
		user.setUserId("1000");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	/**
	 * 在方法入参处@ModelAttribute
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/handle61")
	public String handle61(@ModelAttribute("user") User user) {
		user.setUserId("1000");
		return "/user/createSuccess";
	}
	
	/**
	 * 在此，模型数据会赋给User的入参，
	 * 然后再根据HTTP请求消息进一步填充覆盖user对象
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/handle62")
	public String handle62(@ModelAttribute("user") User user) {
		user.setUserName("tom");
		return "/user/showUser";
	}
	
	/**
	 * 访问UserController中任何一个请求处理方法前，Spring MVC先执行该方法，
	 * 并将返回值以user为键添加到模型中
	 * 
	 * @return
	 */
	@ModelAttribute("user")
	public User getUser() {
		User user = new User();
		user.setUserId("1001");
		return user;
	}
	
	@ModelAttribute("user1")
	public User getUser1() {
		User user = new User();
		user.setUserId("1");
		return user;
	}

	@ModelAttribute("user2")
	public User getUser2() {
		User user = new User();
		user.setUserId("1");
		return user;
	}
	
	@ModelAttribute("dept")
	public Dept getDept() {
		Dept dept = new Dept();
		return dept;
	}
	
	/**
	 * Spring MVC将请求对应的隐含模型
	 * 对象传递给modelMap，因此在方法
	 * 中可以通过它访问模型中的数据。
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/handle63")
	public String handle63(ModelMap modelMap) {
		modelMap.addAttribute("testAttr", "value1");
		User user = (User) modelMap.get("user");
		user.setUserName("tom");
		return "/user/showUser";
	}
	
	/**
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/handle71")
	public String handle71(@ModelAttribute("user") User user) {
		user.setUserName("John");
		return "redirect:handle72.html";
	}
	
	@RequestMapping(value = "/handle72")
	public String handle72(ModelMap modelMap, SessionStatus sessionStatus) {
		User user = (User) modelMap.get("user"); // 读取模型中的数据
		if (user != null) {
			user.setUserName("Jetty");
			sessionStatus.setComplete(); // 让Spring MVC清除本处理器对应的回话属性
		}
		return "/user/showUser";
	}
	
	@RequestMapping(value = "/handle81")
	public String handle81(@RequestParam("user") User user, ModelMap modelMap) {
		modelMap.put("user", user);
		return "/user/showUser";
	}
	
	@RequestMapping(value = "/handle82")
	public String handle82(User user) {
		return "/user/showUser";
	}
	
	@RequestMapping(value = "/handle91")
	public String handle91(@Valid @ModelAttribute("user") User user,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/user/register3";
		} else {
			return "/user/showUser";
		}
	}
	
	/**
	 * 在处理方法中通用代码进行校验
	 * 
	 * @param user
	 * @param bindingResult 声明一个BingdingResult入参
	 * @return
	 */
	@RequestMapping(value = "/handle92")
	public String handle92(@ModelAttribute("user") User user,
			BindingResult bindingResult) {
		// 使用校验工具类进行校验
		ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "userName", "required");
		if ("aaaa".equalsIgnoreCase(user.getUserName())) {
			bindingResult.rejectValue("userName", "reserved");
		} 
		if (bindingResult.hasErrors()) {
			return "/user/register4";
		} else {
			return "/user/showUser";
		}
	}
	
	@RequestMapping(value = "/showUserList")
	public String showUserList(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return "user/userList";
	}
	
	/**
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/showUserListByFtl")
	public String showUserListInFtl(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return "userListFtl";
	}
	
	@RequestMapping(value = "/showUserListByXls")
	public ModelAndView showUserListInExcel(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();

		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return new ModelAndView(new UserListExcelView(), mm);
	}
	
	@RequestMapping(value = "/showUserListByPdf")
	public ModelAndView showUserListInPdf(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();

		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		mm.put("view", new UserListPdfView());
		Map model = new HashMap();  
		mm.addAttribute("userList", userList);
		model.put("userList", userList);
		return new ModelAndView(new UserListPdfView(), model);
	}
	
	@RequestMapping(value = "/showUserListByXml")
	public String showUserListInXml(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		
		return "userListXml";
	}
	
	@RequestMapping(value = "/showUserListByJson")
	public ModelAndView showUserListInJson(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return new ModelAndView("userListJson", mm);
	}
	
	@RequestMapping(value = "/showUserListByJson1")
	public String showUserListInJson1(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆1");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return "userListJson1";
	}

	@RequestMapping(value = "/showUserListByI18n")
	public String showUserListInI18n(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆1");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return "userListi18n";
	}

	@RequestMapping(value = "/showUserListMix")
	public String showUserListMix(ModelMap mm) {
		Calendar calendar = new GregorianCalendar();
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("tom");
		user1.setRealName("汤姆1");
		calendar.set(1980, 1, 1);
		user1.setBirthday(calendar.getTime());
		User user2 = new User();
		user2.setUserName("john");
		user2.setRealName("约翰");
		user2.setBirthday(calendar.getTime());
		userList.add(user1);
		userList.add(user2);
		mm.addAttribute("userList", userList);
		return "userListMix";
	}
	
	@RequestMapping(value = "/uploadPage")
	public String updatePage() {	
		return "uploadPage";
	}
	
	@RequestMapping(value = "/upload")
	public String updateThumb(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			file.transferTo(new File("D:/imps_upload/"+file.getOriginalFilename()));
			return "redirect:success.html";
		}else{
			return "redirect:fail.html";
		}
	}
	
	@RequestMapping(value = "/throwException")
	public String throwException() {
		if(2>1){
			throw new RuntimeException("ddd");
		}
		return "success";
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re, HttpServletRequest request) {
		return "forward:/error.jsp";
	}

	@RequestMapping(value = "/notFound")
	public String notFound() {
		return "successdddd";
	}
	
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFound(HttpServletRequest request) {
		return "forward:/error.jsp";
	}	
	
	/**
	 * 在控制器初始化时调用
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 注册指定自定义的编辑器
//		binder.registerCustomEditor(User.class, new UserEditor());
		// 在进行数据绑定时使用校验器
//		binder.setValidator(new UserValidator());
	}

}
