package 爬虫;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class mulThread extends Thread{
	
	private int start = 0;
	private int end = 1;
	public AtomicInteger count;

	mulThread(int start,int end){
		this.start=start;
		this.end = end;
	}
	
	@Override
	public void run() {
		int fileId=0;
		// for(int num=start;num<end;num++) {
			try {
				fileId = num;
				// String fileName = String.valueOf(num)+".pdf";
				// URL url = new URL("http://www.comap-math.com/mcm/2019Certs/"+fileName);
				// String localPath = "C:\\Users\\yzx\\Desktop\\123\\";
				URL url =new URL("http://localhaost/atest.html");
				// download(url,localPath+fileName);
				int c=download(url);
				System.out.println(c);
			}catch(Exception e) {
				System.out.println(fileId+"没找到");
			}
		// }
	}
	
	public static int download(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		// 设置连接超时时间
		connection.setConnectTimeout(15000);

		// 设置读取超时时间
		connection.setReadTimeout(15000);
		
		// 设置请求参数，即具体的 HTTP 方法
		connection.setRequestMethod("GET");
		
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		if(responseCode==HttpURLConnection.HTTP_OK){
			count++;
		}

		// File file = new File(outputPath);
		// if(!file.getParentFile().exists()) {
		// 	file.getParentFile().mkdir();
		// }
		// FileOutputStream fos = new FileOutputStream(file);
		// // 拿到文件流
		// InputStream is = connection.getInputStream();
		// int len;
		// byte[] b = new byte[1024*1024];
		// while((len = is.read(b)) != -1) {
		//   fos.write(b,0,len);
		// }
		// fos.flush();
		// fos.close();
		// is.close();
		connection.disconnect();
		return count;
	}
}
