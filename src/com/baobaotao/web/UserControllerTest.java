package com.baobaotao.web;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.baobaotao.domain.User;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * 测试类
 */
public class UserControllerTest {

	@Test
	public void testhandle41() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "tom");
		form.add("password", "123456");
		form.add("age", "45");
		restTemplate.postForLocation(
				"http://localhost:8080/user/handle41.html", form);
	}

	@Test
	public void testhandle42() throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		byte[] response = restTemplate.postForObject(
				"http://localhost:8080/user/handle42/{itemId}.html", null,
				byte[].class, "1233");
		Resource outFile = new FileSystemResource("d:/image_copy.jpg");
		FileCopyUtils.copy(response, outFile.getFile());
	}

	@Test
	public void testhandle43() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "tom");
		form.add("password", "123456");
		form.add("age", "45");
		restTemplate.postForLocation(
				"http://localhost:8080/user/handle43.html", form);
	}

	@Test
	public void testhandle44() throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		byte[] response = restTemplate.postForObject(
				"http://localhost:8080/user/handle44/{itemId}.html", null,
				byte[].class, "1233");
		Resource outFile = new FileSystemResource("d:/image_copy.jpg");
		FileCopyUtils.copy(response, outFile.getFile());
	}

	/**
	 * 使用RestTemplate测试
	 * 
	 * @throws IOException
	 */
	@Test
	public void testhandle51() throws IOException {
		RestTemplate restTemplate = buildRestTemplate();

		/**
		 * 创建User对象，它将通过RestTemplate流化为XML请求报文
		 */
		User user = new User();
		user.setUserName("tom");
		user.setPassword("1234");
		user.setRealName("汤姆");

		/**
		 * 指定请求的报文头信息
		 */
		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.setContentType(MediaType
				.valueOf("application/json;UTF-8"));
		entityHeaders.setAccept(Collections
				.singletonList(MediaType.APPLICATION_JSON));

		/**
		 * 将User流化为XML，放到报文体中，同时指定请求方法及报文头。
		 */
		HttpEntity<User> requestEntity = new HttpEntity<User>(user,
				entityHeaders);
		ResponseEntity<User> responseEntity = restTemplate.exchange(
				"http://localhost:8080/user/handle51.html", HttpMethod.POST,
				requestEntity, User.class);

		User responseUser = responseEntity.getBody();
		Assert.assertNotNull(responseUser);
		Assert.assertEquals("1000", responseUser.getUserId());
		Assert.assertEquals("tom", responseUser.getUserName());
		Assert.assertEquals("汤姆", responseUser.getRealName());
	}

	private RestTemplate buildRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		/**
		 * 使用XStream流化器，使用Stax技术处理XML，同时加载使用了XStream注解的User类
		 */
		XStreamMarshaller xmlMarshaller = new XStreamMarshaller();
		xmlMarshaller.setStreamDriver(new StaxDriver());
		xmlMarshaller.setAnnotatedClasses(new Class[] { User.class });

		/**
		 * 创建处理XML报文的HttpMessageConverter，将其组装到RestTemplate中
		 */
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		xmlConverter.setMarshaller(xmlMarshaller);
		xmlConverter.setUnmarshaller(xmlMarshaller);
		restTemplate.getMessageConverters().add(xmlConverter);

		/**
		 * 创建处理JSON报文的HttpMessageConverter，将其组装到RestTemplate中
		 */
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
		restTemplate.getMessageConverters().add(jsonConverter);
		return restTemplate;
	}

	@Test
	public void testhandle61() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "tom");
		form.add("password", "123456");
		form.add("age", "45");
		String html = restTemplate.postForObject(
				"http://localhost:8080/user/handle61.html", form,
				String.class);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.indexOf("1000") > -1);
	}

	@Test
	public void testhandle62() {
		RestTemplate restTemplate = new RestTemplate();
		String html = restTemplate.postForObject(
				"http://localhost:8080/user/handle62.html", null,
				String.class);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.indexOf("1001") > -1);
	}
	
	@Test
	public void testhandle63() {
		RestTemplate restTemplate = new RestTemplate();
		String html = restTemplate.postForObject(
				"http://localhost:8080/user/handle63.html",null,String.class);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.indexOf("1001")>-1);
	}
	
	@Test
	public void testhandle71() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "tom");
		form.add("password", "123456");
		form.add("age", "45");
		restTemplate.postForLocation("http://localhost:8080/user/handle71.html", form);
	}
	
	@Test
	public void testhandle81() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("user", "tom:123456:tomson");
		String html = restTemplate.postForObject(
				"http://localhost:8080/user/handle81.html", form, String.class);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.indexOf("tom") > -1);
	}
	
	@Test
	public void testhandle82() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "tom");
		form.add("password", "123456");
		form.add("age", "45");
		form.add("birthday", "1980-01-01");
		form.add("salary", "4,500.00");
		String html = restTemplate.postForObject(
				"http://localhost:8080/user/handle82.html",form,String.class);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.indexOf("tom")>-1);
	}
	
	@Test
	public void testhandle91() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "tom");
		form.add("password", "12345");
		form.add("birthday", "1980-01-01");
		form.add("salary", "4,500.00");
		String html = restTemplate.postForObject(
				"http://localhost:8080/user/handle91.html",form,String.class);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.indexOf("tom")>-1);
	}

}
