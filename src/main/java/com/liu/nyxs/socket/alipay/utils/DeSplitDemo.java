package com.liu.nyxs.socket.alipay.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeSplitDemo {
	private static final byte BLOCK_START_SIGN = 0x68;
	private static String ENCODING = "UTF-8";

	public List doCanProcess(ByteDataBuffer obj) throws Exception {
		List list = new ArrayList();
		Map bodyMap = new HashMap();
		Map headMap = new HashMap();
		ByteDataBuffer databuf = obj;
		databuf.setEncoding(ENCODING);
		databuf.setInBigEndian(false);
		int totalLen = 0; // 长度
		byte sign = databuf.readInt8();
		if (sign != BLOCK_START_SIGN) {
			System.out.println("无法找到起始标记!");
		}
		totalLen = databuf.readInt32();
		databuf.readString(6);
		byte[] dataBytes = new byte[totalLen];
		databuf.readBytes(dataBytes);
		String message = new String(dataBytes);
		// 报文是json格式，把json报文转换成Map类型的
		JsonToMap gson = new JsonToMap();
		Map messageMap = gson.toMap(message);
		bodyMap = (Map) messageMap.get("body");
		headMap = (Map) messageMap.get("head");
		list.add(headMap);
		list.add(bodyMap);

		return list;
	}

}
