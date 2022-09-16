package com.company;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class CaptureParser {
    static int width;
    static int height;


    public void imageReader() throws IOException {

        BufferedImage img = null;
        File f;

        // read image
        f = new File("src/com/company/DesmosDecimator/Capture.JPG");
        img = ImageIO.read(f);

        // get image width and height
        width = img.getWidth();
        height = img.getHeight();

        int p = img.getRGB(0, 0);


    }

}
