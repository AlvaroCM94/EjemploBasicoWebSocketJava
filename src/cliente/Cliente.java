package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente{
	
	final static int PORT = 40080;
    final static String HOST = "localhost";
    static String nombre = "";

	public static void main(String[] args){
		try{
            Socket sk = new Socket(HOST, PORT);
            
            enviarMensajesAlServidor(sk);
            
        }catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
	}
	
	public String getName(){
		return nombre;
	}
	
	private static void enviarMensajesAlServidor(Socket sk){
        OutputStream os = null;
        InputStream is = null;
        
        try{
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            Scanner sc = new Scanner(System.in);
            String linea;
            
            System.out.println("Escribe tu nombre: ");
            nombre = sc.nextLine();
            
            System.out.println("Escribe la palabra MOSTRAR para ver todo lo escrito hasta ahora: ");
            
            while(true){
                System.out.println("Escribe algo para continuar la historia: ");
                linea = sc.nextLine();
                bw.write(linea);
                bw.newLine();
                bw.flush();
                linea = br.readLine();
                System.out.println("El servidor dice: " + linea);
            }
            
            
        }catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            try{
                if(os != null) os.close();
                
            }catch (IOException ex){
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }

}
