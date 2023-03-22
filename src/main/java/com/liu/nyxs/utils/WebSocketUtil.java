package com.liu.nyxs.utils;

import com.liu.nyxs.domain.AjaxResult;
import com.liu.nyxs.domain.WebsocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebSocketUtil {

	@Autowired(required = false)
	private RestTemplate rest;

	public AjaxResult sendAll(String channel, String message, HttpServletRequest request) {
		return sendAll(getUrlSA(channel, request), channel, message);
	}

	public AjaxResult sendAll(String url, String channel, String message) {
		// 组装信息
		WebsocketMessage wm = new WebsocketMessage();
		wm.setMessage(message);
		// 发送消息
		AjaxResult res = rest.postForObject(url, wm, AjaxResult.class);
		return res;
	}

	public AjaxResult sendOne(String channel, String message, String id, HttpServletRequest request) {
		return sendOne(getUrlSO(channel, request), channel, message, id);
	}

	public AjaxResult sendOne(String url, String channel, String message, String id) {
		// 组装信息
		WebsocketMessage wm = new WebsocketMessage();
		wm.setMessage(message);
		wm.setId(id);
		// 发送消息
		AjaxResult res = rest.postForObject(url, wm, AjaxResult.class);
		return res;
	}

	public String getUrlSA(String channel, HttpServletRequest request) {
		String url = getBaseUrl(request) + "/websocket/" + channel + "/sendAll";
		return url;
	}

	public String getUrlSO(String channel, HttpServletRequest request) {
		String url = getBaseUrl(request) + "/websocket/" + channel + "/sendOne";
		return url;
	}
	
	public static String getBaseUrl(HttpServletRequest req) {
		String baseUrl = req.getScheme()+"://"+req.getServerName();
		if(req.getServerPort()!=80) {
			baseUrl += ":"+req.getServerPort();
		}
		return baseUrl;
	}

}
