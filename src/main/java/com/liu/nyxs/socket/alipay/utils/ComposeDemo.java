package com.liu.nyxs.socket.alipay.utils;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 报文组装
 * 
 * @author chaow
 * @date 2015-11-11
 */
public class ComposeDemo {

	protected byte[] doCanProcess(Map paraMap,String serverCode) throws Exception {
		Gson juJson = new Gson();
		Map map = new HashMap();
		Map headMap = new HashMap();
		// 部分取值都是参考支付宝发送过来的报文头
		// 服务编码
		String servCode = serverCode;
		headMap.put("version", "1.0.1");
		// 对接测试的时候有支付宝提供的常量
		headMap.put("source", "GSCQQIANNENG");
		// 测试环境是longshine
		headMap.put("desIfno", "LONGSHINE");
		// 服务编码
		headMap.put("servCode", servCode);
		// msgId 32位不可重复的流水
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date msgtime = new Date();
		headMap.put("msgId", "ghftest"+sdf.format(msgtime));
		//System.out.println(sdf.format(msgtime));
		headMap.put("msgTime", sdf.format(msgtime));
		headMap.put("extend", "");
		map.put("head", headMap);
		map.put("body", paraMap);
		//System.out.println(map);
		String message = juJson.toJson(map).replace("\\", "");
		 
		System.out.println("测试报文为"+message);
		ByteDataBuffer bdb = new ByteDataBuffer();
		bdb.setInBigEndian(false);
		bdb.writeInt8((byte) 0x68); // 开始字节
		int len = Integer.parseInt(getLen(message, 4));
		// 用于计算数据域的长度是否大于4字节
		bdb.writeInt32(len); // 报文长度
		bdb.writeString(servCode, 6);// 服务编码
		bdb.writeBytes(message.getBytes()); // 报文frame
		bdb.writeInt8((byte) 0x16); // 结束字节
		return bdb.getBytes();
	}
	
	 

	 
	/**
	 * 计算报文长度，不足四位左补零
	 * 
	 * @param text
	 *            报文信息
	 * @param needlen
	 *            报文长度规定的字符数
	 * @return
	 */
	public static String getLen(String text, int needlen) {
		if (text != null) {
			int len;
			try {
				len = text.getBytes("utf-8").length;
			//	System.out.println(len);
				String lenStr = String.valueOf(len);
			//	System.out.println(lenStr);
				StringBuffer sb = new StringBuffer(lenStr);
			//	System.out.println(sb.length());
				while (sb.length() < needlen) {
					sb.insert(0, "0");

				}
				return sb.toString();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
