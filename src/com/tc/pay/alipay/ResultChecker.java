﻿package com.tc.pay.alipay;

import org.json.JSONObject;

/**
 * 瀵圭鍚嶈繘琛岄獙绛?
 * 
 */
public class ResultChecker {

	public static final int RESULT_INVALID_PARAM = 0;
	public static final int RESULT_CHECK_SIGN_FAILED = 1;
	public static final int RESULT_CHECK_SIGN_SUCCEED = 2;

	String mContent;

	public ResultChecker(String content) {
		this.mContent = content;
	}

//	/**
//	 * 浠庨獙绛惧唴瀹逛腑銮峰彇鎴愬姛钟舵?
//	 * 
//	 * @return
//	 */
//	private String getSuccess() {
//		String success = null;
//
//		try {
//			JSONObject objContent = BaseHelper.string2JSON(this.mContent, ";");
//			String result = objContent.getString("result");
//			result = result.substring(1, result.length() - 1);
//
//			JSONObject objResult = BaseHelper.string2JSON(result, "&");
//			success = objResult.getString("success");
//			success = success.replace("\"", "");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return success;
//	}

	/**
	 * 瀵圭鍚嶈繘琛岄獙绛?
	 * 
	 * @return
	 */
	int checkSign() {
		int retVal = RESULT_CHECK_SIGN_SUCCEED;

		try {
			JSONObject objContent = BaseHelper.string2JSON(this.mContent, ";");
			String result = objContent.getString("result");
			result = result.substring(1, result.length() - 1);
			// 銮峰彇寰呯鍚嶆暟鎹?
			int iSignContentEnd = result.indexOf("&sign_type=");
			String signContent = result.substring(0, iSignContentEnd);
			// 銮峰彇绛惧悕
			JSONObject objResult = BaseHelper.string2JSON(result, "&");
			String signType = objResult.getString("sign_type");
			signType = signType.replace("\"", "");

			String sign = objResult.getString("sign");
			sign = sign.replace("\"", "");
			// 杩涜楠岀 杩斿洖楠岀缁撴灉
			if (signType.equalsIgnoreCase("RSA")) {
				if (!Rsa.doCheck(signContent, sign,
						PartnerConfig.RSA_ALIPAY_PUBLIC))
					retVal = RESULT_CHECK_SIGN_FAILED;
			}
		} catch (Exception e) {
			retVal = RESULT_INVALID_PARAM;
			e.printStackTrace();
		}

		return retVal;
	}

//	boolean isPayOk() {
//		boolean isPayOk = false;
//
//		String success = getSuccess();
//		if (success.equalsIgnoreCase("true")
//				&& checkSign() == RESULT_CHECK_SIGN_SUCCEED)
//			isPayOk = true;
//
//		return isPayOk;
//	}
}