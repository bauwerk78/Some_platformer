package bauwerk78.tools;

import java.util.Random;

public interface Randomize {

    static Random rand = new Random();


    static boolean randBoolean() {
        return rand.nextInt(2) == 1;
    }

    static int randPositionX(int min, int max) {
        return rand.nextInt(max) + min;
    }
}
