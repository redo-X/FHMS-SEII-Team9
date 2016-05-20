package warehouse.fh_muenster.de.warehouse;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

/**
 * Created by Thomas on 09.05.2016.
 */
public class Employee {


    private int     employeeNr;
    private String  password;
    private Role    role;
    private int     sessionId;

    private ServerMockImple server;


    public Employee(int employeeNr, String password) {
        this.employeeNr = employeeNr;
        this.password = password;
    }

    public Employee() {
    }

    public int getEmployeeNr() {
        return employeeNr;
    }

    public void setEmployeeNr(int employeeNr) {
        this.employeeNr = employeeNr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "EmployeeNr: " + employeeNr + " Password: " + password;
    }
}
