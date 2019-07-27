
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpTest{
	public static AtomicInteger requestnum=new AtomicInteger();
	public static AtomicInteger count=new AtomicInteger();
	public static void main(String[] args) {
		requestnum.getAndSet(Integer.parseInt(args[0]));
		count.getAndSet(0);
		int threadnum=Integer.parseInt(args[1]);
		String inputurl=args[2];
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadnum); 
		long startTime=System.currentTimeMillis();
		while(requestnum.get()>0) {
			    fixedThreadPool.execute(new Runnable() {
		        @Override
		        public void run() {
			        	try {
							URL url =new URL(inputurl);
							if(download(url)) {
								count.getAndIncrement();
								// System.out.println("count"+count.get());
							}
						}catch(Exception e) {
							System.out.println("not found");
						}
		        }
		       });
			    requestnum.getAndDecrement();
			    // System.out.println("requestnum"+requestnum.get());
			}
		
		try {
			fixedThreadPool.shutdown();
			if(fixedThreadPool.awaitTermination(150000,TimeUnit.MILLISECONDS)) {
				fixedThreadPool.shutdownNow();
			}
		}catch(InterruptedException e){
			System.out.print(e);
			fixedThreadPool.shutdownNow();
		}
		double endTime=System.currentTimeMillis();
		double totalTime=endTime-startTime;
		double requestPerMs=(double)1000*count.get()/totalTime;
		System.out.println("succeed request:"+count.get());
		System.out.println("totalTime(ms):"+totalTime);
		System.out.println("request per second:"+requestPerMs);
		// System.out.println(count.get());
	}
	public static boolean download(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	
		connection.setConnectTimeout(15000);

	
		connection.setReadTimeout(15000);
		
		connection.setRequestMethod("GET");

		connection.setUseCaches(true);
		
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		if(responseCode==HttpURLConnection.HTTP_OK){
			connection.disconnect();
			return true;
		}
		connection.disconnect();
		return false;
		
		
	}
}