package warehouse.fh_muenster.de.warehouse;

import org.junit.Test;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class LoginTaskUnitTest {


    @Test
    public void loginIsCorrect() throws Exception {
        ServerMockImple server = new ServerMockImple();
        Employee employee = new Employee();
        employee = server.login("Bob","123");
        assertNotNull(employee);
    }
    @Test
    public void loginFailed() throws Exception {
        ServerMockImple server = new ServerMockImple();
        Employee employee = new Employee();
        employee = server.login("Bobby","123");
        assertNull(employee);
    }

}