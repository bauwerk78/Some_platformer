package se.lexicon.lars.tools;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class CharacterAnimation {

    List<Image> imageList = new ArrayList<>();
    private String path;
    private String fileName;
    private String fileExtension;
    private int numberOfImages;
    private int counter;


    public CharacterAnimation(String path, String fileName, String fileExtension, int numberOfImages) {
        this.path = path;
        this.fileName = fileName;
        this.numberOfImages = numberOfImages;
        this.fileExtension = fileExtension;

    }

    public void importImages() {
        for (int i = 0; i < numberOfImages; i++) {
            fileName = String.format("%03d", counter);
            imageList.add(new Image("file:" + path + fileName + "." + fileExtension));
            System.out.println(fileName);
            counter++;
        }
    }

    private void init() {

    }

    private void update() {

    }


}//End of class
