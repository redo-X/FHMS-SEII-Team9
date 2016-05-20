package warehouse.fh_muenster.de.warehouse.Server;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by Thomas on 10.05.2016.
 */
public interface ServerMockInterface {

    public Employee login(int employeeNr, String password);

    public void getAllCommissions();

    public HashMap<Integer,Commission> getFreeCommissions();

    public HashMap<Integer,Commission> getCommissions(Employee picker);

}
