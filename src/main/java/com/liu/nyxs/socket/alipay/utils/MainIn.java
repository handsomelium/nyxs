package com.liu.nyxs.socket.alipay.utils;

import com.google.gson.Gson;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedOutputStream;
import java.io.File;
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
		String  acctOrgNo="39536";
		String filePath = "/upload/";
		String fileType = "DZZD";
		String fileName = "GSCQQIANNENG_DZZD_20161207.txt";
		String extend = "";
		//出参定义
		//byte[] by  = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Map <String,String>  mapQuery =  new  HashMap <String,String >();

		String serverCode="200001";

		ComposeDemo cd =  new ComposeDemo();

		//获取连接
		Socket  ss  =  null ;

		if(serverCode.equals("ftp")) {

			// ReadFtpUtil.read();
			return;
		}
		if(serverCode.equals("100001")){
			mapQuery.put("acctOrgNo", acctOrgNo);
			mapQuery.put("filePath", filePath);
			mapQuery.put("fileType", fileType);
			mapQuery.put("fileName", fileName);
			mapQuery.put("extend", extend);
			mapQuery.put("servCode", serverCode);

		ss=MainIn.createSocketNote();
		System.out.println("测试----2"+ss);
		//发包

		BufferedOutputStream  bfo =  new BufferedOutputStream(ss.getOutputStream());
		bfo.write(cd.doCanProcess(mapQuery,serverCode));
		bfo.flush();
		System.out.println("测试----3");

		//收包
 		DeSplitDemo split =  new  DeSplitDemo();

 		final byte[] buf = new byte[36000];



		// 报文帧拆分的处理类
		ss.getInputStream().read(buf);
		ByteDataBuffer bdb = new ByteDataBuffer(buf);
		List tmpList = split.doCanProcess(bdb);
		// 收到的数据
		System.out.println("机构返回的报文=" + tmpList.toString());
		OutputStream writer = ss.getOutputStream();
		Gson gs = new Gson();
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
			mapQuery.put("queryValue", "000030");
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

			Gson gs = new Gson();
			gs.toJson(tmpList);
			System.out.println("-----"+gs.toJson(tmpList));
			// 收到的数据
			System.out.println("机构返回的报文=" + tmpList.toString());
		} else if (serverCode.equals("200002")){

			//080408|2024011500003001580094666355|20240115|10000|1|080408$$202401$#|
			//
			//081691|2024011500003001440093293772|20240115|6435|1|081691$$202401$#|

			//{"bankDateTime":"01:06","extend":"","rcvAmt":"7185","bankDate":"20240115","chargeCnt":"1","payMode":"",
			// "capitalNo":"","rcvDet":"022887|0|202401|$","acctOrgNo":"39536","bankSerial":"2024011500003001900092404487"}

			//085827|2024011800003001620092758192|20240118|51840|1|085827$$202401$#|

			//065900|2024011900003001360093176968|20240119|5000|1|065900$$202401$#|

			//084564|2024012100003001010092954190|20240121|1000|1|084564$$202401$#|

			//000434|2024012200003001490092538389|20240122|100|1|000434$$202401$#|

			//053875|2024012300003001860093076530|20240123|2000|1|053875$$202401$#|


			// 0420013099|2024012400003001930013855533|20240124|3000|1|0420013099$$202401$#|

			// 0410049064|2024012700003001940094139976|20240127|2000|1|0410049064$$202401$#|  2024-01-27 22:02:38

			// 0420009088|2024012900003001500093446131|20240129|1620|1|0420009088$$202401

			// 070893|2024022700003001180098228349|20240227|10000|1|070893$$202402$#|

			// 058402|2024031200003001260011026199|20240312|4798|1|058402$$202403$#|

			// {"04200090381|2024032500003001600099974388|20240325|163620|1|04200090381$$202403$#|":"系统记录不存在"},
			// {"0410129036|2024032500003001750099836063|20240325|5000|1|0410129036$$202403$#|":"系统记录不存在"}

			// 040533|2024040400003001780008805041|20240404|2065|1|040533$$202404$#|
			// 062185|2024040400003001950022726204|20240404|3238|1|062185$$202404$#|
			// 060844|2024040400003001860000513387|20240404|10000|1|060844$$202404$#|
			// 086716|2024040400003001370001191047|20240404|2000|1|086716$$202404$#|
			// 081750|2024040400003001010000542194|20240404|5000|1|081750$$202404$#|
			// 075527|2024040400003001870025298866|20240404|2124|1|075527$$202404$#|
			// 080143|2024040400003001820045468282|20240404|4629|1|080143$$202404$#|
			// 0410079005|2024040400003001030002079675|20240404|6005|1|0410079005$$202404$#|
			// 049226|2024040400003001460001648858|20240404|10000|1|049226$$202404$#|
			// 049190|2024040400003001760000879641|20240404|1800|1|049190$$202404$#|   ***
			// 060857|2024040400003001890001341744|20240404|5282|1|060857$$202404$#|
			// 077283|2024040400003001070001622071|20240404|8000|1|077283$$202404$#|

			// 0410090044|2024040500003001530011421048|20240405|10000|1|0410090044$$202404$#|
			// 045363|2024040500003001790000634467|20240405|3437|1|045363$$202404$#|
			// 049241|2024040500003001720000071006|20240405|1457|1|049241$$202404$#|
			// 060611|2024040500003001970005303582|20240405|3000|1|060611$$202404$#|
			// 030604|2024040500003001130001306649|20240405|18788|1|030604$$202404$#|
			// 080777|2024040500003001110001153120|20240405|4939|1|080777$$202404$#|
			// 070784|2024040500003001540053204013|20240405|5000|1|070784$$202404$#|
			// 058279|2024040500003001900000732705|20240405|5367|1|058279$$202404$#|
			// 0410027089|2024040500003001950022921915|20240405|2000|1|0410027089$$202404$#|
			// 021600|2024040500003001900000735651|20240405|3937|1|021600$$202404$#|
			// 065776|2024040500003001050001430328|20240405|1416|1|065776$$202404$#|  ***
			// 075612|2024040500003001970005340474|20240405|1387|1|075612$$202404$#|
			// 077493|2024040500003001770000681011|20240405|5000|1|077493$$202404$#|
			// 065764|2024040500003001700000199889|20240405|2095|1|065764$$202404$#|
			// 050293|2024040500003001590001843640|20240405|4101|1|050293$$202404$#|
			// 065545|2024040500003001030002198877|20240405|5000|1|065545$$202404$#|

			// 0410165029|2024040600003001860000721823|20240406|1500|1|0410165029$$202404$#|
			// 076458|2024040600003001640001978076|20240406|10000|1|076458$$202404$#|
			// 077082|2024040600003001640001979108|20240406|5310|1|077082$$202404$#|
			// 036389|2024040600003001390004472298|20240406|1534|1|036389$$202404$#| ***
			// 000488|2024040600003001020001527381|20240406|2000|1|000488$$202404$#|
			// 066082|2024040600003001350000449114|20240406|5000|1|066082$$202404$#|
			// 070960|2024040600003001590001939282|20240406|9508|1|070960$$202404$#|
			// 058271|2024040600003001670000996766|20240406|3246|1|058271$$202404$#|
			// 075531|2024040600003001050001561628|20240406|2003|1|075531$$202404$#|
			// 062062|2024040600003001820045758743|20240406|3565|1|062062$$202404$#|
			// 0410201039|2024040600003001810001517524|20240406|3375|1|0410201039$$202404$#|
			// 021733|2024040600003001840001712571|20240406|450|1|021733$$202404$#|
			// 022780|2024040600003001360001256139|20240406|10291|1|022780$$202404$#|
			// 0410199005|2024040600003001830001997988|20240406|3645|1|0410199005$$202404$#|
			mapQuery.put("bankDateTime", "14:16");
			mapQuery.put("acctOrgNo", acctOrgNo);
			mapQuery.put("bankSerial", "2024040600003001830001997988");
			mapQuery.put("bankDate", "20240406");
			mapQuery.put("capitalNo", "");
			mapQuery.put("payMode", "");
			mapQuery.put("rcvAmt", "3645");
			mapQuery.put("chargeCnt", "1");
			mapQuery.put("extend", "");
			mapQuery.put("rcvDet", "0410199005|0|20240406|$");
			mapQuery.put("remark", "系统对账补销账");
			ss=MainIn.createSocket();
			//发包

			BufferedOutputStream  bfo =  new BufferedOutputStream(ss.getOutputStream());
			bfo.write(cd.doCanProcess(mapQuery,serverCode));
			bfo.flush();


	 		DeSplitDemo  split =  new  DeSplitDemo();

	 		final byte[] buf = new byte[36000];

			Gson gs = new Gson();

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
//			String serverHost = "112.74.170.135"; //服务端地址
//			String serverHost = "172.18.3.219";

			String serverHost = "127.0.0.1";
			int serverPort = 1224; //服务端监听端口
			String clientPrivateKey = "ali\\kclient.keystore";  //客户端私钥
			String clientKeyPassword = "longshine";  //客户端私钥密码
			String trustKey = "ali\\tclient.keystore"; //客户端信任证书列表，即服务端证书
			String trustKeyPassword = "longshine";  //客户端信任证书密码

			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			File file = new File("src/main/resources/ali/kclient.keystore");
			ks.load(new FileInputStream(file), clientKeyPassword.toCharArray());
			File file1 = new File("src/main/resources/ali/tclient.keystore");
			tks.load(new FileInputStream(file1), trustKeyPassword.toCharArray());

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

	private static Socket createSocket(){
		try {
			// String serverHost = "112.74.170.135"; //服务端地址
			 String serverHost = "127.0.0.1";
			int serverPort = 1224; //服务端监听端口
			String clientPrivateKey = "alitest\\kclient.keystore";  //客户端私钥
			String clientKeyPassword = "longshine";  //客户端私钥密码
			String trustKey = "alitest\\tclient.keystore"; //客户端信任证书列表，即服务端证书
			String trustKeyPassword = "longshine";  //客户端信任证书密码

			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore tks = KeyStore.getInstance("JKS");

			File file = new File("src/main/resources/ali/kclient.keystore");
			ks.load(new FileInputStream(file), clientKeyPassword.toCharArray());
			File file1 = new File("src/main/resources/ali/tclient.keystore");
			tks.load(new FileInputStream(file1), trustKeyPassword.toCharArray());


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



}
