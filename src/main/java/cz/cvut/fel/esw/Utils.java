package cz.cvut.fel.esw;

public class Utils {
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
}
