package com.liu.nyxs.controller;


import com.liu.nyxs.domain.AjaxResult;
import com.liu.nyxs.domain.WebsocketMessage;
import com.liu.nyxs.utils.DateUtils;
import com.liu.nyxs.utils.WebSocketUtil;
import com.liu.nyxs.webSocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by GuoJM on 2018/12/20.
 */
@Controller
@RequestMapping("/websocket/{channel}")
public class WebSocketController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebSocketUtil websocket;

	/**
	 * 群发消息内容
	 * 
	 * @param message
	 * @return
	 */
	@PostMapping("/sendAll")
	@ResponseBody
	public AjaxResult sendAllMessage(@PathVariable("channel") String channelName, @RequestBody WebsocketMessage wm) {
		String message = wm.getMessage();
		try {
			logger.info("频道：“" + channelName + "”广播消息：" + message);
			WebSocketServer.broadCastInfo(channelName, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AjaxResult.success("在渠道：" + channelName + "群发成功");
	}

	/**
	 * 指定会话ID发消息
	 * 
	 * @param message 消息内容
	 * @param id      连接会话ID
	 * @return
	 */
	@PostMapping(value = "/sendOne")
	@ResponseBody
	public AjaxResult sendOneMessage(@PathVariable("channel") String channelName, @RequestBody WebsocketMessage wm) {
		logger.info("channelName is {}", channelName);
		String message = wm.getMessage();
		String id = wm.getId();
		try {
			WebSocketServer.sendMessage(channelName, id, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AjaxResult.success("在渠道：" + channelName + "发送消息成功");
	}

	/**
	 * 外部调用样例
	 * 
	 * @param message
	 * @return
	 */
	@GetMapping("/sendAllTest")
	@ResponseBody
	public Object sendAllMessage(@PathVariable("channel") String channelName, HttpServletRequest request) {
		// websocket.sendAll方法可以直接传websocket接口地址，也可以通过HttpServletRequest获取
		// websocket.sendAll(url, channelName, "后端发送测试"+DateUtil.date2String(new Date(),
		// DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS));
		return websocket.sendAll(channelName,
				"后端发送测试" + DateUtils.getTime(), request);
	}
}