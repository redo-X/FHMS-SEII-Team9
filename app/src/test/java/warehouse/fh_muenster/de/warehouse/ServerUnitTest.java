package warehouse.fh_muenster.de.warehouse;

import org.junit.Test;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Server.Server;
import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ServerUnitTest {

    /**
     * Testet ob der Login als Kommissionierer erfolgreich ist
     */
    @Test
    public void loginIsCorrect(){
        ServerMockImple server = new ServerMockImple();
        Employee employee = new Employee();
        employee = server.login(1,"geheim");
        assertNotNull(employee);
    }
    /**
     * Testet ob der Login fehlschlägt ist
     */
    @Test
    public void loginFailed(){
        ServerMockImple server = new ServerMockImple();
        Employee employee = new Employee();
        employee = server.login(234,"123");
        assertNull(employee);
    }
    /**
     * Testet ob der Login als Lagerist erfolgreich ist
     */
    @Test
    public void loginAsStock(){
        ServerMockImple server = new ServerMockImple();
        Employee employee = new Employee();
        employee = server.login(2,"geheim");
        assertTrue(employee.getRole().equals(Role.Lagerist));
    }
    /**
     * Testet ob offene Kommissionen geladen werden können
     */
    @Test
    public void getFreeCommissions(){
        ServerMockImple server = new ServerMockImple();
        HashMap<Integer,Commission> hashMap;
        hashMap = server.getFreeCommissions(1);
        assertNotNull(hashMap);
    }
    /**
     * Testet ob zugewiesende Kommissionen geladen werden können
     */
    @Test
    public void getPickerCommissions(){
        ServerMockImple server = new ServerMockImple();
        HashMap<Integer,Commission> hashMap;
        Employee employee = new Employee(1,1);
        hashMap = server.getCommissions(1,employee);
        assertNotNull(hashMap);
    }
    /**
     * Testet ob Artikel zur Kommission geladen werden können
     */
    @Test
    public void getPosistionCommissions(){
        ServerMockImple server = new ServerMockImple();
        HashMap<Integer,Article> hashMap;
        hashMap = server.getPositionToCommission(1,1);
        assertNotNull(hashMap);
    }
    /**
     * Testet ob alle Artikel geladen werden können
     */
    @Test
    public void getArticles(){
        ServerMockImple server = new ServerMockImple();
        HashMap<String,Article> hashMap;
        hashMap = server.getArticles(1);
        assertNotNull(hashMap);
    }
}