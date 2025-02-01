package com.gymapp.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRService {

    /** 
    * Creates QR Code from {@code String}.
    * @param data      -    information which will be contained in output
    * @param path      -    path to file where output will be stored
    * @param charset   -    {@linkplain java.nio.charset.Charset charset} used for encoding/decoding {@code String}
    * @param hashmap   -    specify {@code EncodeHintType} and {@code ErrorCorrectionLevel}
    * @param height    -    height of output image
    * @param width     -    width of output image
    */
    public static void createQR(String data, String path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width) throws  IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        
        MatrixToImageWriter.writeToPath(matrix, path.substring(path.lastIndexOf('.') + 1), Path.of(path));
    }

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
     * @param image     -   {@linkplain java.awt.image.BufferedImage BufferedImage}
     * @return              {@code String} contained in QR Code or {@code null}
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
