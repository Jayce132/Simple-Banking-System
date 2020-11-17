package banking;

import view.View;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("-fileName")) {
            View view = new View(args[1]);
            view.start();
        }
    }
}