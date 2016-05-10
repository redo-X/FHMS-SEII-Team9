package warehouse.fh_muenster.de.warehouse.Server;

import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by Thomas on 10.05.2016.
 */
public interface ServerMockInterface {

    public Employee login(String username, String password);
}
