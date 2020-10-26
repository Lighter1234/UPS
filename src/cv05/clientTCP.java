package cv05;
import java.io.*;
import java.net.*;

class clientTCP
{

 public static void main(String argv[]) throws Exception {
  String sentence;
  String modifiedSentence;
  Socket socket = new Socket("127.0.0.1", 10001);
  InetAddress adresa = socket.getInetAddress();
  System.out.print("Pripojuju se na : "+adresa.getHostAddress()+" se jmenem : "+adresa.getHostName()+"\n" );
  ReadThread rt = new ReadThread(socket);
  WriteThread wt = new WriteThread(socket);
  rt.start();
  wt.start();

  rt.join();
  wt.join();

  socket.close();
 }
}
