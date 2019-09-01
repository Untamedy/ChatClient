/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.procesing;

import chatclient.entities.Message;
import chatclient.entities.User;
import static chatclient.procesing.Login.LOGGER;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class UserList {

    public UserList() {
    }

    public static List<User> fromJSON(InputStream input) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> listOfUser = new ArrayList();
        try {
            listOfUser = (List<User>) mapper.readValue(input, List.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserList.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listOfUser;
    }

    public static List<User> send(String url) {
        List<User> list = new ArrayList<>();
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("Get");
            conn.setDoOutput(true);

            if (conn.getResponseCode() == 200) {
                list = fromJSON(conn.getInputStream());
            }

        } catch (MalformedURLException ex) {
            LOGGER.warning(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.warning(ex.getMessage());
        }
        return list;
    }

   
}
