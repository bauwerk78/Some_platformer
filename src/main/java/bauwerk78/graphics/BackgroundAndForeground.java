package bauwerk78.graphics;

import bauwerk78.implementer.MainGame;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import bauwerk78.model.Level;


public class BackgroundAndForeground {

    private Region backgroundRegion = new Region();
    private Pane foregroundPane = new Pane();
    private String backgroundImagePathAndName;
    private String foregroundImagePathAndName;

    //Example path "file:Images/image.png"
    public BackgroundAndForeground(String backgroundImagePathAndName, String foregroundImagePathAndName) {
        this.backgroundImagePathAndName = backgroundImagePathAndName;
        this.foregroundImagePathAndName = foregroundImagePathAndName;
        init();
    }

    public void init() {

        Image backgroundImage = new Image(backgroundImagePathAndName);
        Image foregroundImage = new Image(foregroundImagePathAndName);
        BackgroundImage backgroundImage1 = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(MainGame.windowWidth, MainGame.windowHeight, false, false, false, false));
        BackgroundImage foregroundImage1 = new BackgroundImage(foregroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(MainGame.windowWidth, MainGame.windowHeight, false, false, false, false));

        //The background.
        Background background = new Background(backgroundImage1);
        backgroundRegion.setBackground(background);
        backgroundRegion.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        backgroundRegion.setVisible(true);

        //The foreground.
        Background foreground = new Background(foregroundImage1);
        foregroundPane.setBackground(foreground);
        foregroundPane.setPrefSize(Level.levelW * Level.TILESIZE, Level.levelH * Level.TILESIZE);
        foregroundPane.setVisible(true);
    }

    public Region getBackgroundRegion() {
        return backgroundRegion;
    }

    public Pane getForegroundPane() {
        return foregroundPane;
    }

}//End of class.

