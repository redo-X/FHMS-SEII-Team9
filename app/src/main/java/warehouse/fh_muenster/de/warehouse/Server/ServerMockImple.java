package warehouse.fh_muenster.de.warehouse.Server;

import android.util.Log;

import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by Thomas on 10.05.2016.
 */
public class ServerMockImple implements ServerMockInterface {
    @Override
    public Employee login(int employeeNr, String password) {
        if(employeeNr == 123 && password.equals("123")){
            Employee user = new Employee(123, "123");
            return user;
        }
        else{
            return null;
        }
    }
}
