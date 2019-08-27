/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.procesing;

import chatclient.entities.Message;
import chatclient.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class UserList {
    
    private static  List<User> listOfUser = new ArrayList();

    
     public static Message fromJSON(InputStream input) {  
         ObjectMapper mapper = new ObjectMapper();
        try {
            listOfUser = mapper.readValue(input, UserList.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }

        return message;
    }
    
    
}
