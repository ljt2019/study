package cn.healthmall.sail.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class BigDecimalUtils
{
	/** 0.01 */
	public static final BigDecimal DOTZN = new BigDecimal("0.01");
	/** 0 */
	public static final BigDecimal ZERO = BigDecimal.ZERO;
	/** 0.1 */
	public static final BigDecimal ONEPOINTER = new BigDecimal("0.1");
	/** 1 */
	public static final BigDecimal ONE = BigDecimal.ONE;
	/** -1 */
	public static final BigDecimal _ONE = new BigDecimal("-1");
	/** 10 */
	public static final BigDecimal TEN = BigDecimal.TEN;
	/** -10 */
	public static final BigDecimal _TEN = new BigDecimal("-10");
	/** 100 */
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	/** -100 */
	public static final BigDecimal _ONE_HUNDRED = new BigDecimal("-100");
	/** 1,000 */
	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
	/** -1,000 */
	public static final BigDecimal _ONE_THOUSAND = new BigDecimal("-1000");
	/** BigDecimal 控件最大值 */
	public static final BigDecimal MAX_BIGDECIMAL = new BigDecimal("1.0E18");
	/** BigDecimal 控件最小值 */
	public static final BigDecimal MIN_BIGDECIMAL = new BigDecimal("-1.0E18");

	/** 取得数值 */
	public static BigDecimal getValue(Object value) {
		if (value == null) {
			return ZERO;
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else if (value instanceof Integer) {
			return BigDecimal.valueOf((Integer)value);
		} else if (value instanceof Long) {
			return BigDecimal.valueOf((Long)value);
		} else if (value instanceof Double) {
			return BigDecimal.valueOf((Double)value);
		} else if (value instanceof Float) {
			return BigDecimal.valueOf((Float)value);
		} else if (value instanceof String) {
			return getValue((String) value);
		}
		return ZERO;
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(Object value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(Object value, int scale, int roundType) {
		return getValue(value).setScale(scale, roundType);
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(BigDecimal value) {
		return value == null ? ZERO : value;
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(BigDecimal value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 取得数值 */
	public static BigDecimal getValue(Integer value) {
		return value == null ? ZERO : BigDecimal.valueOf(value.longValue());
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(Integer value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 取得数值 */
	public static BigDecimal getValue(Long value) {
		return value == null ? ZERO : BigDecimal.valueOf(value.longValue());
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(Long value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 取得数值 */
	public static BigDecimal getValue(Float value) {
		return value == null ? ZERO : BigDecimal.valueOf(value.floatValue());
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(Float value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 取得数值 */
	public static BigDecimal getValue(Double value) {
		return value == null ? ZERO : BigDecimal.valueOf(value.doubleValue());
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(Double value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 取得数值 */
	public static BigDecimal getValue(String value) {
		return StringUtils.isEmpty(value) ? ZERO : new BigDecimal(value);
	}
	
	/** 取得数值 */
	public static BigDecimal getValue(String value, int scale) {
		return getValue(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 是否为0 */
	public static boolean isZeroOrNull(BigDecimal value) 
	{
		if (value == null) {
			return true;
		}
		return ZERO.compareTo(value) == 0;
	}

}

