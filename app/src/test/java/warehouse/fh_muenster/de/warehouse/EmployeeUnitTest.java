package warehouse.fh_muenster.de.warehouse;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Thomas on 10.05.2016.
 */
public class EmployeeUnitTest {

    @Test
    public void login_isCorrect(){
        Employee user = new Employee("Bob","123");
        assertTrue("Login ist Fehlgeschlagen",user.login());
    }
}
