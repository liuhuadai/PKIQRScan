package cn.com.encrypt;

import java.io.*;

public class GetPubKey {
    public String getPublicKey(){
        File file =new File("D:\\rsa_public_key.pem");
        if(file.isFile()){
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuffer sb = new StringBuffer();
                String lineText = null;
                while((lineText=bufferedReader.readLine())!=null){
                    if(lineText.charAt(0)!='-'){
                        sb.append(lineText);
                    }
                }
                bufferedReader.close();
                reader.close();
                System.out.println(sb.toString());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
