package cv05;
import java.io.*;
import java.net.*;

class clientTCP
{

 public static void main(String argv[]) throws Exception {
  String sentence;
  String modifiedSentence;
  Socket socket = new Socket("85.163.114.202", 4242);
  System.out.println(socket.isConnected());
//   ReadThread rt = new ReadThread(socket);
  WriteThread wt = new WriteThread(socket);
//   rt.start();
  wt.start();

//  rt.join();
  wt.join();

  socket.close();
 }
}
