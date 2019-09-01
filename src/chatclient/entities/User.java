package chatclient.entities;

/**
 *
 * @author YBolshakova
 */
public class User {
    private String name;
    private String password;
    private String status = "off";
    private String room;

    public User() {
    }    

    public User(String name, String password) {
        this.name = name;
        this.password = password;        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    
    
    @Override
    public String toString() {
        return "User{" + "name=" + name + ", status=" + status + '}';
    }

}
