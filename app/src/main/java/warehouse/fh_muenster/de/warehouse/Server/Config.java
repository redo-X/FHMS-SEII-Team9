package warehouse.fh_muenster.de.warehouse.Server;

/**
 * Created by Thomas on 17.06.2016.
 */
public class Config {
    static boolean isMock = true;

    public static boolean isMock() {
        return isMock;
    }

    public static void setIsMock(boolean isMock) {
        Config.isMock = isMock;
    }




}
