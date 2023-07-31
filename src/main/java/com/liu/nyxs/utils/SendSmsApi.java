package com.liu.nyxs.utils;
import net.sf.json.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 融合云信短信发送
 */
public class SendSmsApi {


	public static final int TIMEOUT = 10000;
	private static String host = "http://rcsapi.wo.cn:8000/umcinterface/sendtempletmsg";



	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		// 商户编码
		String cpcode = "AADBYY";
		// 手机号
		String mobiles = "13975072600";
		// 模板参数值，多个的话用逗号隔开
		String msg = "411205";
		// 渠道自定义接入号的扩展码，可为空
		String excode = "471355";
		// 模板id
		String templetid = "2948114";

		String key = "eb0f04eb6b1e05e4ff0a65fe273d171e";
		String md5source = cpcode + msg + mobiles + excode+ templetid+key;
        System.out.println("--签名串-"+md5source);
		String sign = makeMD5(md5source).toLowerCase();
		json.put("cpcode", cpcode);
		json.put("msg", msg);
		json.put("excode", excode);
		json.put("mobiles", mobiles);
		json.put("templetid", templetid);
		json.put("sign", sign);
		System.out.println("---sign---"+sign);
		System.out.println(post(host, json.toString(), "UTF-8"));
		
	}



	/**加密**/
	public static String makeMD5(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_md5;
	}

	/**发送短信**/
	public static String post(String url, String datastr, String charset) {
		if (url == null || !url.toLowerCase().startsWith("http")) {
			return null;
		}
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).build();
			HttpPost post = new HttpPost(url.trim());
			post.setConfig(requestConfig);
			if (datastr != null) {
				if (charset != null) {
					post.setEntity(new StringEntity(datastr, charset));
				} else {
					post.setEntity(new StringEntity(datastr));
				}
			}
			CloseableHttpResponse response = httpclient.execute(post);
			String result = null;
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
			}
			httpclient.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}