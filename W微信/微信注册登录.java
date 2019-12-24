package cn.healthmall.sail.app.wrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cn.healthmall.sail.app.dto.SsoLoginResultDTO;
import cn.healthmall.sail.app.dto.SsoLoginUserDTO;
import cn.healthmall.sail.common.config.SSOUrlConfigProperties;
import cn.healthmall.sail.common.dto.AppResultObj;
import cn.healthmall.sail.common.util.JsonUtils;
import cn.healthmall.sail.common.util.RSAUtils;

/**
 * SSO登录相关
 * @author wangZL
 *
 */
@Service
public class SsoService {

	private static final Logger LOG = LoggerFactory.getLogger(SsoService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SSOUrlConfigProperties ssoUrlConfigProperties;
	
	public static final String appId = "102";
	
	/**
	 * 获取时间错
	 * @return
	 */
	public Long getTimestamp(){
		
		String url = ssoUrlConfigProperties.getV3Timestamp();
		String str = restTemplate.getForObject(url, String.class);
		if(StringUtils.isNotBlank(str)){
			try {
				AppResultObj appResultObj = JsonUtils.fromJson(str, AppResultObj.class);
				if (appResultObj != null && appResultObj.isSucceed()) {
					return Long.parseLong(appResultObj.getData().toString());
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	/**
	 * 微信登录
	 * @param openId
	 * @param unionId
	 * @return
	 */
	public SsoLoginUserDTO login(String openId, String unionId){
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("grantType", "app_credential");
		dataMap.put("openId", openId);
		dataMap.put("unionId", unionId);
		String dataJson = JsonUtils.toJson(dataMap);
		LOG.info("login params: {}", dataJson);
		
		dataJson = RSAUtils.getHexData(ssoUrlConfigProperties.getPublickey(), dataJson);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("appId", appId);
		headers.add("timeStamp", getTimestamp().toString());
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("data", dataJson);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		String str = restTemplate.postForObject(ssoUrlConfigProperties.getV3Login(), request, String.class);
		LOG.info("login result: {}", str);
		SsoLoginUserDTO ssoLoginUserDTO = null;
		if(StringUtils.isNotBlank(str)){
			try {
				AppResultObj appResultObj = JsonUtils.fromJson(str, AppResultObj.class);
				if(appResultObj.getCode() == AppResultObj.CODE_OK){
					SsoLoginResultDTO ssoLoginResultDTO = JsonUtils.fromJson(JsonUtils.toJson(appResultObj.getData()), SsoLoginResultDTO.class);
					ssoLoginUserDTO = ssoLoginResultDTO.getUserVo();
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ssoLoginUserDTO;
	}
	
	/**
	 * 注册发送验证码
	 * @param mobile
	 * @return
	 */
	public AppResultObj sendRegCode(String mobile, String smsType){
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("grantType", "app_credential");
		dataMap.put("telephone", mobile);
		dataMap.put("smsType", smsType);
		dataMap.put("opType", "usr_reg");
		
		String dataJson = JsonUtils.toJson(dataMap);
		LOG.info("sendRegCode params: {}", dataJson);
		
		dataJson = RSAUtils.getHexData(ssoUrlConfigProperties.getPublickey(), dataJson);
		HttpHeaders headers = new HttpHeaders();
		headers.add("appId", appId);
		headers.add("timeStamp", getTimestamp().toString());
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("data", dataJson);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		String str = restTemplate.postForObject(ssoUrlConfigProperties.getV3SmsSend(), request, String.class);
		LOG.info("sendRegCode result: {}", str);
		if(StringUtils.isNotBlank(str)){
			try {
				AppResultObj appResultObj = JsonUtils.fromJson(str, AppResultObj.class);
				if(appResultObj.getCode() != AppResultObj.CODE_OK){
					appResultObj.setSucceed(false);
				}
				return appResultObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return null;
	}
	
	/**
	 * 校验验证码
	 * @param mobile
	 * @param code
	 * @return
	 */
	public AppResultObj verifyRegCode(String mobile, String code){
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("grantType", "app_credential");
		dataMap.put("telephone", mobile);
		dataMap.put("veriCode", code);
		dataMap.put("opType", "usr_reg");
		
		String dataJson = JsonUtils.toJson(dataMap);
		LOG.info("sendRegCode params: {}", dataJson);
		
		dataJson = RSAUtils.getHexData(ssoUrlConfigProperties.getPublickey(), dataJson);
		HttpHeaders headers = new HttpHeaders();
		headers.add("appId", appId);
		headers.add("timeStamp", getTimestamp().toString());
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("data", dataJson);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		String str = restTemplate.postForObject(ssoUrlConfigProperties.getV3Verify(), request, String.class);
		LOG.info("sendRegCode result: {}", str);
		if(StringUtils.isNotBlank(str)){
			try {
				AppResultObj appResultObj = JsonUtils.fromJson(str, AppResultObj.class);
				if(appResultObj.getCode() != AppResultObj.CODE_OK){
					appResultObj.setSucceed(false);
				}
				return appResultObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 注册
	 * @param mobile
	 * @param smsToken
	 * @param userPassword
	 * @return
	 */
	public AppResultObj register(String mobile, String smsToken, String userPassword){
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("grantType", "app_credential");
		dataMap.put("telephone", mobile);
		dataMap.put("userPassword", userPassword);
		dataMap.put("smsToken", smsToken);
		dataMap.put("regType", "1001");
		
		String dataJson = JsonUtils.toJson(dataMap);
		LOG.info("register params: {}", dataJson);
		
		dataJson = RSAUtils.getHexData(ssoUrlConfigProperties.getPublickey(), dataJson);
		HttpHeaders headers = new HttpHeaders();
		headers.add("appId", appId);
		headers.add("timeStamp", getTimestamp().toString());
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("data", dataJson);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		String str = restTemplate.postForObject(ssoUrlConfigProperties.getV3Register(), request, String.class);
		LOG.info("register result: {}", str);
		if(StringUtils.isNotBlank(str)){
			try {
				AppResultObj appResultObj = JsonUtils.fromJson(str, AppResultObj.class);
				if(appResultObj.getCode() != AppResultObj.CODE_OK){
					appResultObj.setSucceed(false);
				}
				return appResultObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
