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
     * Sendet einen Login request an den Server mit der Mitarbeiter Nummer und dem Passwort.
     * Liefert im erfolgsfall einen Mitarbeiter Objekt zurück.
     *
     */
    public Employee login(int employeeNr, String password);

    /**
     * Sendet einen Logout request mit der sessionId an den Server.
     * @param sessionId
     */
    public void logout(int sessionId);

    /**
     * Fragt alle Positionen die zu einer bestimmten Kommission gehört an.
     * Die Kommission Id wird übergeben
     * Es wird eine Hashmap mit allen Artikeln zu der zugehörigen Kommission geliefert.
     * Schlüssel ist die Kommissions positions Id, Value ist der Article
     */
    public HashMap<Integer, Article> getPositionToCommission(int id);

    /**
     *Übersendet die Kommissions Id an den Server um die Kommission zu starten.
     */
    public void startCommission(int commissionId);

    /**
     *Übersendet die Kommissions Id an den Server um die Kommission zu beenden.
     */
    public void endCommission(int commissionId);

    /**
     * Aktualisiert die Kommissionierte menge. Sendet die Kommissionierte positionid
     * und die kommissionierte menge an den Server.
     */
    public void updateQuantityOnCommissionPosition(int commissionPositionId, int quantity);

    /**
     * Sendet eine Fehlmenge an den Server. Es wird die Session id des Mitarbiters, die kommissions
     * position id und die fehlmenge übergeben.
     */
    public void commitCommissionMessage(int sessionId, int commissionPositionId, int differenceQuantity, String note);

    /**
     * Weißt einen Mitarbiter eine Kommission zu. Übersendet die KommissionsId die angenommen werden soll
     * und die Mitarbiter nummer.
     */
    public void allocateCommission(int commissionId, int employeeId);

    /**
     * Ändert den Lagerbestand. Es wird der zu ändernde articleCode und die zu änderne Menge übergeben
     */
    public void commitStock(String artikelCode, int menge);

    /**
     * Holt alle Freien Kommissionen.
     * Liefert eine HashMap mit der KommissionsId als Schlüssel und der Kommission als Wert.
     * @return
     */
    public HashMap<Integer, Commission> getFreeCommissions();

    /**
     * Holt alle Kommissionen die zu einem Mitarbiter gehören.
     * Liefert eine HashMap mit der KommissionsId als Schlüssel und der Kommission als Wert.
     */
    public HashMap<Integer, Commission> getCommissions(Employee picker);

    /**
     * Liefert eine HashMap die alle Artikle enthält. Schlüssel ist der Artikle Code und der Wert ist der Article
     *
     */
    public HashMap<String, Article> getArticles(int sessionId);

}
