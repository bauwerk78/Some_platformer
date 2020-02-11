package se.lexicon.lars.graphics;

import javafx.scene.layout.Pane;

public class Background {

    private Pane pane = new Pane();


    public Background() {
        init();
    }

    private void init() {
        pane.setPrefSize(Renderer.windowWidth, Renderer.windowHeight);

    }

}
