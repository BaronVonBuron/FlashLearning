package com.example.flashlearning;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageConverter {

    public static Image byteArrayToFXImage(byte[] byteArray) throws IOException {

        BufferedImage bufferedImage;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray)) {
            bufferedImage = ImageIO.read(inputStream);
        }

        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
