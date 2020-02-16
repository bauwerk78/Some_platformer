package se.lexicon.lars.tools;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class CharacterAnimation {

    List<Image> imageList = new ArrayList<>();
    private String path;
    private String fileName;
    private int numberOfImages;


    public CharacterAnimation(String path, String fileName, int numberOfImages) {
        this.path = path;
        this.fileName = fileName;
        this.numberOfImages = numberOfImages;

    }

    public void importImages() {
        for (int i = 0; i < numberOfImages; i++) {
            imageList.add(new Image("file:"+ path + fileName));
        }
    }

    private void init() {

    }

    private void update() {

    }


}//End of class
