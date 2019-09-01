package chatclient.procesing;

import chatclient.source.Utils;
import chatclient.entities.Message;
import chatclient.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetThread implements Runnable {

    private final ObjectMapper mapper;
    private int n;
    private User user;

    public GetThread(User user) {
        mapper = new ObjectMapper();
        this.user = user;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                URL url = new URL(Utils.getURL() + "/get?from=" + n+"&room="+user.getRoom());
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();
                try {
                    byte[] buf = requestBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);

                    JsonMessages list = mapper.readValue(strBuf, JsonMessages.class);
                    if (list != null) {
                        for (Message m : list.getList()) {
                            if(!m.isIsprivate()){
                                System.out.println(m);
                            }else{  
                                if(user.getName().equalsIgnoreCase(m.getTo())){
                                System.out.println(m);   
                                }
                            }
                           n++; 
                        }
                    }
                } finally {
                    is.close();
                }
                Thread.sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;
        do {
            r = is.read(buf);
            if (r > 0) {
                bos.write(buf, 0, r);
            }
        } while (r != -1);

        return bos.toByteArray();
    }
}
