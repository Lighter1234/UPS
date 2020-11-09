package cv04;

import java.io.*;
import java.lang.ClassNotFoundException;
import java.lang.Runnable;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.*;
import java.util.Random;

//-Djava.net.preferIPv4Stack=true
 
public class serverTCPThread
{

	public static void main(String[] args )
	{

            	try {
    			ServerSocket server = new ServerSocket( 10001, 10, InetAddress.getByName("localhost") );
			int vlakna = 0;	
			while (true) 
			{
				System.out.print( "Server ceka ...\n" );
                		Socket socket = server.accept();
				vlakna++;
				System.out.print( "Pripojen klient \n");
				new serverTCPThreadWork( socket, vlakna ).start();
			}
            	} 
		catch (Exception e) 
		{
                	e.printStackTrace();
            	}
        }
}
class serverTCPThreadWork extends Thread
{
	Socket socket;
	int idVlakna;
	int random;
	Random r = new Random(System.currentTimeMillis());
	public serverTCPThreadWork( Socket socket, int idVlakna )
	{
		this. socket = socket;
		this.idVlakna = idVlakna;
		random = r.nextInt(100);
	}

	public void run() 
	{
		System.out.print( "Spoustim vlakno s ID = "+idVlakna+"\n" );
		InetAddress adresa = socket.getInetAddress();
		System.out.print("Pripojil se klient z: "+adresa.getHostAddress()+"/"+adresa.getHostName()+"\n" );

		BufferedReader ois = null;
		String message;
		OutputStream oos = null;
		try
		{
			ois = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = ois.readLine();
			oos = socket.getOutputStream();

           		while(true){
					System.out.print("Prijimam zpravu " + message);

					if(message.equals("HELLO")){
						String s = "NUM:"+ random;
						oos.write(s.getBytes());
						while(true){
							String num = ois.readLine();
							if(Integer.parseInt(num) == random*2){
								oos.write("OK".getBytes());
							}else{
								oos.write("WRONG".getBytes());
							}
							break;
						}

					}else{
						oos.write("ERROR".getBytes());
					}
					System.out.println("Message Received: " + message);
					ois.close();
					oos.close();
					socket.close();
					System.out.print("Ukoncuji vlakno s ID="+idVlakna+"\n");
				}

		}
		catch(NumberFormatException e){
			try {
				oos.write("ERROR".getBytes());
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

		catch( Exception e2 )
		{
			e2.printStackTrace();
		}
	}
}

