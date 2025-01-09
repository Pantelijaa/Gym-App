package com.gymapp.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

/**
 * Reads QR Code from {@code File} or {@code BufferedImage}.
 * Has no Constructor nor attributes
 * @see QRgenerator
 */

public class QRreader {
    /**
     * 
     * @param qrCodeImage   -   {@linkplain java.io.File File} which contains QR Code
     * @return                  {@code String} contained in QR Code or {@code null}
     * @throws IOException
     */
    public String decodeQRCode(File qrCodeImage) throws IOException{
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
        return decodeQRCode(bufferedImage);
    }
    /**
     * 
     * @param image -   {@linkplain java.awt.image.BufferedImage BufferedImage}
     * @return          {@code String} contained in QR Code or {@code null}
     */
    public String decodeQRCode(BufferedImage image) {
        Result result;                   
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            return null;
        }
    }
}
