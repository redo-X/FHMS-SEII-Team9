package warehouse.fh_muenster.de.warehouse;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

/**
 * Created by Thomas on 09.05.2016.
 */
public class Employee {


    private String username;
    private String password;

    private ServerMockImple server;


    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "Username: " + username + " Password: " + password;
    }
}
