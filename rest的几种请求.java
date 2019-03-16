package com.mobile.mobilemap.manage.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mobile.mobilemap.common.dto.AppResultObj;
import com.mobile.mobilemap.common.mybatis.base.BaseServiceImpl;
import com.mobile.mobilemap.common.mybatis.base.IBaseMapper;
import com.mobile.mobilemap.common.mybatis.base.PageParameter;
import com.mobile.mobilemap.common.mybatis.query.Query;
import com.mobile.mobilemap.manage.dao.QrCodeCircuitRentMapper;
import com.mobile.mobilemap.manage.dto.InterfaceTypeA;
import com.mobile.mobilemap.manage.dto.ProductList;
import com.mobile.mobilemap.manage.dto.QrCodeCircuitRentOutDTO;
import com.mobile.mobilemap.manage.dto.RestInDTO;
import com.mobile.mobilemap.manage.entity.QrCodeCircuitRent;
import com.mobile.mobilemap.manage.entity.QrCodeCircuitRentExample;
import com.mobile.mobilemap.manage.service.QrCodeCircuitRentService;
import com.mobile.mobilemap.manage.utils.JsonUtils;

@Service
@Transactional
public class QrCodeCircuitRentServiceImpl extends BaseServiceImpl<QrCodeCircuitRent, QrCodeCircuitRentExample>
		implements QrCodeCircuitRentService {

	@Autowired
	private QrCodeCircuitRentMapper baseMapper;

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${httpclient.devMgmt.arStartUrl}")
	private String startUrl;

	@Override
	public IBaseMapper<QrCodeCircuitRent, QrCodeCircuitRentExample> getBaseMapper() {
		return baseMapper;
	}

	@Override
	public List<QrCodeCircuitRent> query(Query query, PageParameter pageParameter) {
		return baseMapper.query(query, pageParameter);
	}

	@Override
	public AppResultObj postJsonRest(RestInDTO param) {

		try {
			// 设置请求头
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);

			// 请求参数
			String jsonData = JsonUtils.toJson(param);
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);

			// 请求url
			String url = param.getUrl();

			logger.info("========= postJson请求URL ：【{}】", url);
			logger.info("========= postJson请求入参 ：【{}】", JsonUtils.toJson(jsonData));

			AppResultObj appResultObj = restTemplate.postForObject(url, requestEntity, AppResultObj.class);

			logger.info("========= postJson请求成功！ ：【{}】", JsonUtils.toJson(appResultObj));

			return appResultObj;
		} catch (Exception e) {
			logger.info("========= postJson请求失败  =========【{}】", e.getMessage());
			return AppResultObj.parameterError();
		}
	}

	@Override
	public AppResultObj postFormRest(RestInDTO param) {
		try {

			// 请求头
			HttpHeaders headers = new HttpHeaders();
//			headers.set("xx", xx);

			// 请求体
			MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//			body.add("xx", yy);

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body,
					headers);

			// 请求url
			String url = param.getUrl();

			logger.info("========= postForm请求URL ：【{}】", url);
			logger.info("========= postForm请求入参 ：【{}】", JsonUtils.toJson(entity));

			AppResultObj ret = restTemplate.postForObject(url, entity, AppResultObj.class);
			logger.info("========= postForm请求成功！ ：【{}】", JsonUtils.toJson(ret));
			return ret;
		} catch (Exception e) {
			logger.info("========= postForm请求失败  =========【{}】", e.getMessage());
			return AppResultObj.parameterError();
		}
	}

	@Override
	public AppResultObj getRest(RestInDTO param) {
		try {
			// 请求url
			String url = param.getUrl();

			logger.info("========= get请求URL ：【{}】", url);

			AppResultObj ret = restTemplate.getForObject(url, AppResultObj.class);


			logger.info("========= get请求成功！ ：【{}】", JsonUtils.toJson(ret));
			return ret;
		} catch (Exception e) {
			logger.info("========= get请求失败  =========【{}】", e.getMessage());
			return AppResultObj.parameterError();
		}
	}
}