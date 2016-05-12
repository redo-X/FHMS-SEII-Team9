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

    @Override
    public String toString(){
        return "Username: " + username + " Password: " + password;
    }
}
