package warehouse.fh_muenster.de.warehouse;

/**
 * Created by futur on 13.05.2016.
 */
public enum Role {
    Kommissionierer(0),
    Lagerist(1),
    Administrator(2);

    private final int code;
    private Role(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public static Role fromInt(int code) {
        switch (code) {
            case 2:
                return Kommissionierer;
            case 1:
                return Lagerist;
            default:
                return Kommissionierer;
        }
    }

    public String toString() {
        return String.format("%s: %s", this.name(), String.valueOf(this.code));
    }
}
