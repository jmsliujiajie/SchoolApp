package com.lll.utisl;


/**
 * webservice 公用处理类，包括参数加、解密，接入ip限制等
 */

public class WsPubSecur {

	public static final String PAGO_DATA = "PAGO_DATA";// 分页使用的key
	public static final String LIST_DATA = "LIST_DATA";// 普通列表
	public static final String RET_JSON_OBJ = "retJsonObj";

	/**
	 * 参数加密
	 */

	public static String encrypt(String jsonStr) {
		return ThreeDesSecret.encrypt(jsonStr);
	}

	/**
	 * 参数解密
	 */
	public static String decypt(String jsonStr) {
		return ThreeDesSecret.decypt(jsonStr);
	}

}
