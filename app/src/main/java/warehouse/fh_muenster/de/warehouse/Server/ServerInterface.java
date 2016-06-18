package warehouse.fh_muenster.de.warehouse.Server;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Article;
import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by Thomas on 10.05.2016.
 * Enthält alle Methoden um mit dem Server zu kommunizieren
 */
public interface ServerInterface {
    /**
     * Liefert bei erfolgreicher anmeldung am Server ein Employee Objekt zurück.
     * Andernfalls wird Null zurück geliefert
     * @param employeeNr Mitarbeiter Nummer des Employess
     * @param password  Passwort des Mitarbiters
     * @return Employee der sich angemeldet hat
     */
    public Employee login(int employeeNr, String password);

    /**
     * Sendet einen Logout request mit der sessionId an den Server.
     * @param sessionId Session Id des Angemldeten Mitarbeiters
     */
    public void logout(int sessionId);

    /**
     * Fragt alle Positionen die zu einer bestimmten Kommission gehört an.
     * Die Kommission Id wird übergeben
     * Es wird eine Hashmap mit allen Artikeln zu der zugehörigen Kommission geliefert.
     * Schlüssel ist die Kommissions positions Id, Value ist der Article
     * @param id Id der Kommission
     * @return HashMap mit allen Articeln die zur Kommission gehören
     */
    public HashMap<Integer, Article> getPositionToCommission(int sessionId, int id);

    /**
     *Übersendet die Kommissions Id an den Server um die Kommission zu starten.
     * @param commissionId Id der zu startenden Kommission
     */
    public void startCommission(int sessionId, int commissionId);

    /**
     *Übersendet die Kommissions Id an den Server um die Kommission zu beenden.
     * @param commissionId Id der zu beenden Kommission
     */
    public void endCommission(int sessionId, int commissionId);

    /**
     * Aktualisiert die Kommissionierte menge. Sendet die Kommissionierte positionid
     * und die kommissionierte menge an den Server.
     * @param commissionPositionId Position des Artikles
     * @param quantity Kommissionierte Menge
     */
    public void updateQuantityOnCommissionPosition(int sessionId, int commissionPositionId, int quantity);

    /**
     * Sendet eine Fehlmenge an den Server. Es wird die Session id des Mitarbiters, die kommissions
     * position id und die fehlmenge übergeben.
     * @param sessionId Session id des Mitarbiters
     * @param commissionPositionId Position des Artikels
     * @param differenceQuantity Fehlmenge
     * @param note Beschreibung
     */
    public void commitCommissionMessage(int sessionId, int commissionPositionId, int differenceQuantity, String note);

    /**
     * Weißt einen Mitarbiter eine Kommission zu. Übersendet die KommissionsId die angenommen werden soll
     * und die Mitarbiter nummer.
     * @param commissionId Id der Kommission
     * @param employeeId Mitarbeiter Nummer
     */
    public void allocateCommission(int sessionId, int commissionId, int employeeId);

    /**
     * Ändert den Lagerbestand. Es wird der zu ändernde articleCode und die zu änderne Menge übergeben
     * @param artikelCode Code es Artikels
     * @param menge Zu ändernde Menge
     */
    public void commitStock(int sessionId, String artikelCode, int menge);

    /**
     * Holt alle Freien Kommissionen.
     * Liefert eine HashMap mit der KommissionsId als Schlüssel und der Kommission als Wert.
     * @return HashMap mit allen freien Kommissionen
     */
    public HashMap<Integer, Commission> getFreeCommissions(int sessionId);

    /**
     * Holt alle Kommissionen die zu einem Mitarbiter gehören.
     * Liefert eine HashMap mit der KommissionsId als Schlüssel und der Kommission als Wert.
     * @param picker Mitarbeiter objekt
     * @return HashMap mit allen Kommission die zum Mitarbiter gehören
     */
    public HashMap<Integer, Commission> getCommissions(int sessionId, Employee picker);

    /**
     * Liefert eine HashMap die alle Artikle enthält. Schlüssel ist der Artikle Code und der Wert ist der Article
     * @param sessionId Session Id des Mitarbiters
     * @return HashMap mit allen Artikeln
     */
    public HashMap<String, Article> getArticles(int sessionId);

    /**
     * Legt einen neuen Artikle auf dem Server an.
     * @param sessionId Session Id des Mitarbiters
     * @param articleCode Artikle Code des Artikels der gespeichert werden soll
     * @param articleName Name des Artikles der gespeichert werden soll
     * @param lagerort Lagerort des Artikels
     * @return true wenn der Artikle gespeichert werden kann sonst false
     */
    public boolean createArticle(int sessionId, String articleCode, String articleName, String lagerort);
}
