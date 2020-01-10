package se.lexicon.lars.tools;

import static se.lexicon.lars.graphics.Renderer.elapsedTime;

public class Delayer {

    private double counter;
    private double timeToDelay;

    public Delayer() {

    }

    public Delayer(double timeToDelay) {
        this.timeToDelay = timeToDelay;
    }

    public boolean delayTimer(double timeToDelay) {
        counter += elapsedTime;
        //System.out.println("counter: " + counter + " timeToDelay: " + timeToDelay);
        if (counter < timeToDelay) {
            return false;
        } else {
            counter = 0;
            return true;
        }
    }

}
