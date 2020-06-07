/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expedient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.scene.image.Image;

/**
 *
 * @author isac
 */
public class apiEncodeBase64 {
//    public static String encoder(String imagePath) {
//        String base64Image = "";
//        File file = new File(imagePath);
//
//        try (FileInputStream imageInFile = new FileInputStream(file)) {
//            byte imageData[] = new byte[(int) file.length()];
//            imageInFile.read(imageData);
//            base64Image = Base64.getEncoder().encodeToString(imageData);
//        } catch (Exception e) {
//            System.out.println("Imagen no encontrada: " + e);
//        }
//        return base64Image;
//    }
    
    public static String encoder(File file) {
        String base64Image = "";

        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (Exception e) {
            System.out.println("Imagen no encontrada: " + e);
        }
        return base64Image;
    }

    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);

            ByteArrayInputStream bis = new ByteArrayInputStream(imageByteArray);

            Image img = new Image(bis);

        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }
}
