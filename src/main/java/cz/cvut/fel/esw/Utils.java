package cz.cvut.fel.esw;

public class Utils {
    public static final int MESSAGE_LENGTH = 4;
    public static final int INITIAL_DATABASE_CAPACITY = 5_000_000;
    public static boolean isPort(String arg) {
        int i;
        try {
            i = Integer.parseInt(arg);
            if ( !(0 < i && i < 65536) ){
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static int arrToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));
    }
}
