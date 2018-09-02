package cn.healthmall.sail.base.enums;

import java.util.Objects;

public enum GymStateEnum {
	OPEN_FREE_DEVICE(0, "营业中且有空闲状态跑步机"),
	OPEN_NO_DEVICE(1, "营业中且无空闲状态跑步机"),
	CLOSE(2, "休业");

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	private GymStateEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static GymStateEnum getByCode(Integer code) {
		if (Objects.nonNull(code)) {
			for (GymStateEnum value : GymStateEnum.values()) {
				if (value.code == code) {
					return value;
				}
			}
		}
		return null;
	}
}
