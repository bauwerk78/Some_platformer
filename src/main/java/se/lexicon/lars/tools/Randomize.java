package se.lexicon.lars.tools;

import java.util.Random;

public interface Randomize {

    static Random rand = new Random();
    static boolean randBoolean() {
        if(rand.nextInt(2) == 1) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
            return false;
        }
   }

    static int randPositionX(int min, int max) {
        return rand.nextInt(max) + min;
    }
}
