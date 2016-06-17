package warehouse.fh_muenster.de.warehouse.Server;

/**
 * Created by Thomas on 17.06.2016.
 */
public class Config {
    static boolean isMock = true;
    static boolean isMockScanner = false;
    public static boolean isMock() {
        return isMock;
    }

    public static void setIsMock(boolean isMock) {
        Config.isMock = isMock;
    }

    public static boolean isMockScanner() {
        return isMockScanner;
    }

    public static void setIsMockScanner(boolean isMockScanner) {
        Config.isMockScanner = isMockScanner;
    }
}
