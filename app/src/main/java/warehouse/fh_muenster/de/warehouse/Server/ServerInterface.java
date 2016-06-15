package warehouse.fh_muenster.de.warehouse.Server;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Article;
import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by Thomas on 10.05.2016.
 */
public interface ServerInterface {

    /**
     * Send a Login Request to the Server.
     * If login successful a Employee returns.
     * Else a null Object returns
     * @param  employeeNr
     * @param password
     * @return a Employee
     */
    public Employee login(int employeeNr, String password);

    /**
     * Send a Logout request to the Server
     * @param sessionId
     */
    public void logout(int sessionId);

    //public void getPositionToCommission(int id);

    //public HashMap<Integer, Article> getPositionToCommission(int id);

    public HashMap<Integer,Commission> getFreeCommissions();

    public HashMap<Integer,Commission> getCommissions(Employee picker);

}
