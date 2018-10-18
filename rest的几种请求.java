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

			String json = JsonUtils.toJson(ret.getData()).replace("A", "a").replace("Z", "z")
					.replace("IPVPN_per_month_fee", "iPVPN_per_month_fee")
					.replace("IP_per_month_fee", "iP_per_month_fee");

			QrCodeCircuitRentOutDTO out = JsonUtils.fromJson(json, QrCodeCircuitRentOutDTO.class);
			
			//实体类
			QrCodeCircuitRent qrCodeCircuitRent = new QrCodeCircuitRent();

			// 协议
			BeanUtils.copyProperties(out, qrCodeCircuitRent);

			// 产品
			ProductList product = out.getProductList().get(0);
			logger.info("========= get请求成功product！ ：【{}】", JsonUtils.toJson(product));
			qrCodeCircuitRent.setProduct_id(product.getId());
			qrCodeCircuitRent.setProduct_business_note(product.getBusiness());
			qrCodeCircuitRent.setProduct_business_note(product.getBusiness_note());
			qrCodeCircuitRent.setProduct_count(product.getCount());
			qrCodeCircuitRent.setProduct_accept_type(product.getAccept_type());
			qrCodeCircuitRent.setProduct_accept_type_note(product.getAccept_type_note());
			qrCodeCircuitRent.setProduct_usage(product.getUsage());
			qrCodeCircuitRent.setProduct_ip_count(product.getIp_count());
			qrCodeCircuitRent.setProduct_type(product.getType());
			qrCodeCircuitRent.setProduct_contract_id(product.getContract_id());

			// 接口
			InterfaceTypeA a = out.getProductList().get(0).getInterfaceList().get(0).getA();
			InterfaceTypeA z = out.getProductList().get(0).getInterfaceList().get(0).getZ();
			qrCodeCircuitRent.setInterface_id(a.getId()+"|"+z.getId());
			qrCodeCircuitRent.setInterfacet_entry_name(a.getEntry_name()+"|"+z.getEntry_name());
			qrCodeCircuitRent.setInterface_entry_address(a.getEntry_address()+"|"+z.getEntry_address());
			qrCodeCircuitRent.setInterface_entry_way(a.getEntry_way()+"|"+z.getEntry_way());
			qrCodeCircuitRent.setInterface_entry_way_note(a.getEntry_way_note()+"|"+z.getEntry_way_note());
			qrCodeCircuitRent.setInterface_entry_type(a.getEntry_type()+"|"+z.getEntry_type());
			qrCodeCircuitRent.setInterface_entry_type_note(a.getEntry_type_note()+"|"+z.getEntry_type_note());
			qrCodeCircuitRent.setInterface_entry_contact(a.getEntry_contact()+"|"+z.getEntry_contact());
			qrCodeCircuitRent.setInterface_entry_phone(a.getEntry_phone()+"|"+z.getEntry_phone());
			qrCodeCircuitRent.setInterface_entry_fee(a.getEntry_fee()+"|"+z.getEntry_fee());
			qrCodeCircuitRent.setInterface_entry_rate(a.getEntry_rate()+"|"+z.getEntry_rate());
			qrCodeCircuitRent.setInterface_port_type(a.getPort_type()+"|"+z.getPort_type());
			qrCodeCircuitRent.setInterface_product_id(a.getProduct_id()+"|"+z.getProduct_id());
			qrCodeCircuitRent.setInterface_group_id(a.getGroup_id()+"|"+z.getGroup_id());
			qrCodeCircuitRent.setInterface_extra_info(a.getExtra_info()+"|"+z.getExtra_info());
			
			baseMapper.insert(qrCodeCircuitRent);

			logger.info("========= get请求成功！ ：【{}】", JsonUtils.toJson(ret));
			return ret;
		} catch (Exception e) {
			logger.info("========= get请求失败  =========【{}】", e.getMessage());
			return AppResultObj.parameterError();
		}
	}
}