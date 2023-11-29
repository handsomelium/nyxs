package com.liu.nyxs.socket.alipay.utils;

import com.google.gson.Gson;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainIn {

	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		//文件通知服务
		String  acctOrgNo="37447";
		String filePath = "/upload/";
		String fileType = "DZZD";
		String fileName = "GSCQQIANNENG_DZZD_20231017.txt";
		String extend = "";
		//出参定义
		//byte[] by  = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Map <String,String>  mapQuery =  new  HashMap <String,String >();
		
		Gson gs = new Gson();
		String  serverCode="200001";
		
		ComposeDemo cd =  new ComposeDemo();	
	
		//获取连接
		Socket  ss  =  null ;
		
		if(serverCode.equals("200011")){
			mapQuery.put("acctOrgNo", acctOrgNo);
			mapQuery.put("filePath", filePath);
			mapQuery.put("fileType", fileType);
			mapQuery.put("fileName", fileName);
			mapQuery.put("extend", extend);
			
		ss=MainIn.createSocket();
		System.out.println("测试----2"+ss);
		//发包
		
		BufferedOutputStream  bfo =  new BufferedOutputStream(ss.getOutputStream());
		bfo.write(cd.doCanProcess(mapQuery,serverCode));
		bfo.flush();
		System.out.println("测试----3");
	 
		//收包
 		DeSplitDemo  split =  new  DeSplitDemo();
		 
 		final byte[] buf = new byte[36000];
		
	  
		 
		// 报文帧拆分的处理类
		ss.getInputStream().read(buf);
		ByteDataBuffer bdb = new ByteDataBuffer(buf);
		List tmpList = split.doCanProcess(bdb);
		// 收到的数据
		System.out.println("机构返回的报文=" + tmpList.toString());
		OutputStream writer = ss.getOutputStream();
		gs.toJson(tmpList);
		System.out.println("-----"+gs.toJson(tmpList));
//		System.out.println("应答的数据为="
//				+ new String(cd.doCanProcess(mapQuery ,serverCode), "UTF-8"));
//		writer.write(cd.doCanProcess(mapQuery,serverCode));
//		writer.flush();
//		writer.close();
		ss.close();
		} else if (serverCode.equals("200001")){
			mapQuery.put("acctOrgNo", acctOrgNo);
			mapQuery.put("queryType", "0");
			mapQuery.put("queryValue", "000000001");
			mapQuery.put("bgnYm", "");
			mapQuery.put("endYm", "");
			mapQuery.put("busiType", "11");
			mapQuery.put("extend", "");
			
			ss=MainIn.createSocket(); 
			System.out.println("测试----2   "+ss);
			//发包
			
			BufferedOutputStream  bfo =  new BufferedOutputStream(ss.getOutputStream());
			bfo.write(cd.doCanProcess(mapQuery,serverCode));
			bfo.flush();
			System.out.println("测试----3");
		
			 
	 		DeSplitDemo  split =  new  DeSplitDemo();
			 
	 		final byte[] buf = new byte[36000];		
		  
			 
			// 报文帧拆分的处理类
			ss.getInputStream().read(buf);
			ByteDataBuffer bdb = new ByteDataBuffer(buf);
			List tmpList = split.doCanProcess(bdb);
			
			gs.toJson(tmpList);
			System.out.println("-----"+gs.toJson(tmpList));
			// 收到的数据
			System.out.println("机构返回的报文=" + tmpList.toString());
		} else if (serverCode.equals("200002")){
			mapQuery.put("acctOrgNo", acctOrgNo);
			mapQuery.put("bankSerial", sdf.format(date));
			mapQuery.put("bankDate", sdf1.format(date));
			mapQuery.put("capitalNo", "");
			mapQuery.put("payMode", "");
			mapQuery.put("rcvAmt", "13000");
			mapQuery.put("chargeCnt", "1");
			mapQuery.put("extend", "");
			mapQuery.put("rcvDet", "66778899||202310||$");
			ss=MainIn.createSocket(); 
			System.out.println("测试----2"+ss);
			//发包
			
			BufferedOutputStream  bfo =  new BufferedOutputStream(ss.getOutputStream());
			bfo.write(cd.doCanProcess(mapQuery,serverCode));
			bfo.flush();
			System.out.println("测试----3");
		
			 
	 		DeSplitDemo  split =  new  DeSplitDemo();
			 
	 		final byte[] buf = new byte[36000];		
		  
			 
			// 报文帧拆分的处理类
			ss.getInputStream().read(buf);
			ByteDataBuffer bdb = new ByteDataBuffer(buf);
			List tmpList = split.doCanProcess(bdb);
			gs.toJson(tmpList);
			System.out.println("-----"+gs.toJson(tmpList));
			// 收到的数据
			System.out.println("机构返回的报文=" + tmpList.toString());
		}else if (serverCode.equals("200012")){
			mapQuery.put("acctOrgNo", acctOrgNo);
			mapQuery.put("bankSerial", "20231013151544");
			mapQuery.put("bankDate", "20231013");
			mapQuery.put("extend", "");
			ss=MainIn.createSocket();
			System.out.println("测试----2"+ss);
			//发包

			BufferedOutputStream  bfo =  new BufferedOutputStream(ss.getOutputStream());
			bfo.write(cd.doCanProcess(mapQuery,serverCode));
			bfo.flush();
			System.out.println("测试----3");


			DeSplitDemo  split =  new  DeSplitDemo();

			final byte[] buf = new byte[36000];


			// 报文帧拆分的处理类
			ss.getInputStream().read(buf);
			ByteDataBuffer bdb = new ByteDataBuffer(buf);
			List tmpList = split.doCanProcess(bdb);
			gs.toJson(tmpList);
			System.out.println("-----"+gs.toJson(tmpList));
			// 收到的数据
			System.out.println("机构返回的报文=" + tmpList.toString());
		}
	
		
		
	}
	
	private static Socket createSocketNote(){
		try {
			String serverHost = "112.124.212.137"; //服务端地址
			int serverPort = 6665; //服务端监听端口
			String clientPrivateKey = ".\\key\\kclient.keystore";  //客户端私钥
			String clientKeyPassword = "longshine";  //客户端私钥密码
			String trustKey = ".\\key\\tclient.keystore"; //客户端信任证书列表，即服务端证书
			String trustKeyPassword = "longshine";  //客户端信任证书密码
			
			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			ks.load(new FileInputStream(clientPrivateKey), clientKeyPassword.toCharArray());
			tks.load(new FileInputStream(trustKey), trustKeyPassword.toCharArray());

			kmf.init(ks, clientKeyPassword.toCharArray());
			tmf.init(tks);

			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			return (Socket) ctx.getSocketFactory().createSocket(serverHost, serverPort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; 
		
	}
	
	/*private static Socket createSocket(){
		try {
			String serverHost = "127.0.0.1"; //服务端地址
			int serverPort = 1224; //服务端监听端口
			String clientPrivateKey = ".\\key\\kclient.keystore";  //客户端私钥
			String clientKeyPassword = "longshine";  //客户端私钥密码
			String trustKey = ".\\key\\tclient.keystore"; //客户端信任证书列表，即服务端证书
			String trustKeyPassword = "longshine";  //客户端信任证书密码
			
			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			ks.load(new FileInputStream(clientPrivateKey), clientKeyPassword.toCharArray());
			tks.load(new FileInputStream(trustKey), trustKeyPassword.toCharArray());

			kmf.init(ks, clientKeyPassword.toCharArray());
			tmf.init(tks);

			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			return (Socket) ctx.getSocketFactory().createSocket(serverHost, serverPort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; 
		
	}*/

	private static Socket createSocket(){
		try {
			String serverHost = "58.252.73.14"; //服务端地址
			int serverPort = 1224; //服务端监听端口
			String clientPrivateKey = "D:\\test\\kclient.keystore";  //客户端私钥
			String clientKeyPassword = "longshine";  //客户端私钥密码
			String trustKey = "D:\\test\\tclient.keystore"; //客户端信任证书列表，即服务端证书
			String trustKeyPassword = "longshine";  //客户端信任证书密码

			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			ks.load(new FileInputStream(clientPrivateKey), clientKeyPassword.toCharArray());
			tks.load(new FileInputStream(trustKey), trustKeyPassword.toCharArray());

			kmf.init(ks, clientKeyPassword.toCharArray());
			tmf.init(tks);

			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			return  ctx.getSocketFactory().createSocket(serverHost, serverPort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




}
