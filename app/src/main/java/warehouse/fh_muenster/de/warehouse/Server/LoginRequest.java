package warehouse.fh_muenster.de.warehouse.Server;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

/**
 * Created by Thomas on 10.06.2016.
 */
public class LoginRequest extends SoapObject {

    private static final long serialVersionUID = 2357803949211970026L;

    private Integer employeeNr;
    private String password;
    /**
     * @return the employeeNr
     */
    public Integer getEmployeeNr() {
        return employeeNr;
    }
    /**
     * @param employeeNr the employeeNr to set
     */
    public void setEmployeeNr(Integer employeeNr) {
        this.employeeNr = employeeNr;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
