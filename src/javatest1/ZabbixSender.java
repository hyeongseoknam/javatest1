package javatest1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class ZabbixSender {
	
	public static void sendData(String hostguid, String item, String data)throws IOException{
		int connectTimeout = 20;
		String host = "";
		int port=10051;
		Socket socket = null;
		try { 
			socket = new Socket(); 
			socket.connect(new InetSocketAddress(host, port), connectTimeout); 

			InputStream inputStream = socket.getInputStream(); 
			OutputStream outputStream = socket.getOutputStream();
			byte[] payload = buildJSonString( hostguid, item, data).getBytes("utf8");
			writeMessage(outputStream, payload);
			byte[] recvBuf = new byte[512];
			int nbytesThisTime = inputStream.read(recvBuf);
			String response= new String(recvBuf,0, nbytesThisTime,Charset.forName("ut8"));
			validateResponse( response);
		}finally{
			if(socket != null) socket.close();			
		}
	}

	private static void validateResponse(String response){
		
		
	}

	private static String buildJSonString(String host, String item, String value)
	{
		return 		  "{"
        + "\"request\":\"sender data\",\n"
        + "\"data\":[\n"
        +        "{\n"
        +                "\"host\":\"" + host + "\",\n"
        +                "\"key\":\"" + item + "\",\n"
        +                "\"value\":\"" + value.replace("\\", "\\\\") + "\"}]}\n" ;
	}
 
	private static void writeMessage(OutputStream out, byte[] data) throws IOException {
		int length = data.length;
 
		out.write(new byte[] {
				'Z', 'B', 'X', 'D', 
				'\1',
				(byte)(length & 0xFF), 
				(byte)((length >> 8) & 0x00FF), 
				(byte)((length >> 16) & 0x0000FF), 
				(byte)((length >> 24) & 0x000000FF),
				'\0','\0','\0','\0'});
 
		out.write(data);
	}
}
