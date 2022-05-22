package cz.cvut.fel.esw;

public class Main {
    public static void main(String args []){
        int port = 8080;
        if ( args.length > 0) {
            System.out.println( "Parameters found, using first suitable integer as port number. " +
                                "Port needs to be between 0 and 65535");
            for (String i : args){
                if (Utils.isPort(i)){
                    port = Integer.parseInt(i);
                    break;
                }
            }
        }
        Server server = new Server(port);
        new Thread(server).start();
    }
}
