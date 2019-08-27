package chatclient.procesing;

import chatclient.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 *
 * @author YBolshakova
 */
public class Login {

    public static final Logger LOGGER = Logger.getLogger(Login.class.getName());

    public static User fromJson(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {
            user = mapper.readValue(inputStream, User.class);
        } catch (JsonProcessingException ex) {
            LOGGER.warning(ex.getMessage());
        }
        return user;
    }

    

    public static User send(String url) {
        StringBuilder content;
        User user = null;

        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("Get");
            conn.setDoOutput(true);           

            if (conn.getResponseCode() == 200) {
                user = fromJson(conn.getInputStream());
            }

        } catch (MalformedURLException ex) {
            LOGGER.warning(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.warning(ex.getMessage());
        }
        return user;
    }

}
