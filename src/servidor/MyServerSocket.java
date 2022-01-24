package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServerSocket extends Thread{
	
	Socket sk;
	ArrayList<String> palabras = new ArrayList<String>();
	ArrayList<String> ips = new ArrayList<String>();
	
    public MyServerSocket(Socket sk){
        this.sk = sk;
    }

    @Override
    public void run(){
        InputStream is = null;
        OutputStream os = null;
        try{
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            Inet4Address ip = (Inet4Address) sk.getInetAddress();
            String laIP = ip.getHostAddress();
            
            while(true){
                String linea = br.readLine();
                if(linea.equals("MOSTRAR")){
                	System.out.println("La historia hasta ahora: " + "\n" + all());
                	
                }else{
                	System.out.println(laIP +": " + linea);
                    palabras.add(linea);
                    ips.add(laIP);
                    
                }
                
                bw.write("Mensaje enviado");
                bw.newLine();
                bw.flush();
            }
            
        }catch (IOException ex){
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            try{
                if(is != null) is.close();
                
            }catch (IOException ex){
                Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }
    
    private String all(){
    	String res = "";
    	for(int i = 0; i < palabras.size(); i++){
    		res += "La ip " + ips.get(i) + " escribio: " + palabras.get(i) + "\n";
    	}
    	return res;
    }
}
