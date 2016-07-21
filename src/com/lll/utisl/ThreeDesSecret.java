package com.lll.utisl;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.kobjects.base64.Base64;

public class ThreeDesSecret {

//	private final static String THREEDESKEY = System.getProperty("WS_SEC_THREEDESKEY");//"0BB9548598196D68D0433D910116F20DC432ABF2D6F40845";
	private final static String THREEDESKEY = "0BB9548598196D68D0433D910116F20DC432ABF2D6F40845";
//	private final static String IVSPEC = System.getProperty("WS_SEC_IVSPEC");//"71a34cE0";
	private final static String IVSPEC = "71a34cE0";
	private final static String encoding = "utf-8";

	public static String encrypt(String plainText) {

		try {
			Key deskey = null;
			DESedeKeySpec spec;
			spec = new DESedeKeySpec(THREEDESKEY.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IVSPEC.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return Base64.encode(encryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String decypt(String encryptText) {
		byte[] decryptData = null;
		try {
			Key deskey = null;
			DESedeKeySpec spec;
			spec = new DESedeKeySpec(THREEDESKEY.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IVSPEC.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			decryptData = cipher.doFinal(Base64.decode(encryptText));
			return new String(decryptData, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("加密：" + encrypt("{R:{'retcode':'000000'}}"));
		System.out.println("解密：" + decypt("cosWbXmXjcKuJx5CY10WTbZ8OjSCjvlm6AJX1m8B4E0="));
	}
}
