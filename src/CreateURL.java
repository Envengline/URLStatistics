import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class CreateURL {

        public static void main(String[] args) throws IOException {

            int urlsKind = 1000000;
            String[] urls = new String[urlsKind];

            for (int i=0; i<urlsKind; i++){
                urls[i] = getRandomURL();
            }
            int urlsNum = 250000000;
            for (int i=0; i<urlsNum; i++){
                writeFileURL(urls[new Random().nextInt(urlsKind)],"C:\\Users\\kitty\\Desktop\\测试数据\\test.txt");
            }
            System.out.println("over");
        }

        private static void writeFileURL(String url, String outFilepath) throws IOException {

            File file=new File(outFilepath);
            if(!file.exists()){
                if(!file.createNewFile()){
                    System.out.println("预处理创建文件失败..");
                    return;
                }
            }
            FileOutputStream out=new FileOutputStream(file,true);
            out.write((url +"\r\n").getBytes("utf-8"));
            out.close();
        }
        private static String getRandomURL(){
            String[] protocol = {"http","ftp","https","smtp"};
            String home = "qwertyuiopasdfghjklzxcvbnm"
                    + "QWERTYUIOPASDFGHJKLZXCVBNM";
            StringBuilder res = new StringBuilder();

            res.append(protocol[new Random().nextInt(protocol.length)]).append("://");//1
            int randomNameNum  = new Random().nextInt(20)+4;
            StringBuilder hostname = new StringBuilder();
            for(int i=0; i<randomNameNum; i++){
                hostname.append(home.charAt(new Random().nextInt(home.length())));
            }
            res.append(hostname).append(":");//2
            res.append(new Random().nextInt(9999)+1);//3

            int path  = new Random().nextInt(5)+1;
            for(int i=0; i<path; i++){
                StringBuilder myPath = new StringBuilder();
                int myPathlen  = new Random().nextInt(10)+5;
                for(int j=0; j<myPathlen; j++){
                    myPath.append(home.charAt(new Random().nextInt(home.length())));
                }
                res.append("/").append(myPath);
            }
            return res.toString();
        }

}
