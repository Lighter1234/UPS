package cv04;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

		ObjectInputStream ois = null;
		String message;
		ObjectOutputStream oos = null;
		try
		{
			ois = new ObjectInputStream(socket.getInputStream());
			message = (String) ois.readObject();
			oos = new ObjectOutputStream(socket.getOutputStream());

           		while(true){
					System.out.print("Prijimam zpravu " + message);



					if(message.equals("HELLO")){
						oos.writeObject("NUM:"+ random);
						while(true){
							String num = (String) ois.readObject();
							if(Integer.parseInt(num) == random*2){
								oos.writeObject("OK");
							}else{
								oos.writeObject("WRONG");
							}
							break;


						}

					}else{
						oos.writeObject("ERROR");

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
				oos.writeObject("ERROR");
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

