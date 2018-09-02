package cn.healthmall.sail.base.enums;

import cn.healthmall.sail.common.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created By User: RXK Date: 2017/9/26 Time: 17:13 Version: V1.0
 * Description:场官方营业状态
 */
public enum GymStatus implements BaseEnum<GymStatus, Integer> {
	BUSINESS(0, "正常营业"),
	SUSPEND_BUSINESS(1, "歇业"),
	CLOSE(2, "关闭");

	private Integer code;
	private String msg;

	GymStatus(Integer code, String msg) {
		this.code = code;
		this.msg = msg;

	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static GymStatus getValue(Integer code) {
		if (Objects.nonNull(code)) {
			for (GymStatus value : GymStatus.values()) {
				if (value.code == code) {
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * 使用 集合 封装所有的枚举类
	 */
	static Map<Integer, GymStatus> map = new HashMap<>();
	static {
		for (GymStatus value : GymStatus.values()) {
			map.put(value.getCode(), value);
		}
	}

	/**
	 * 获取指定的枚举
	 * 
	 * @param code
	 * @return
	 */
	public static GymStatus getCode(Integer code) {
		return map.get(code);
	}

	@JsonValue
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", getCode());
		map.put("msg", getMsg());
		return map;
	}

}
