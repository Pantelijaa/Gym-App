package com.gymapp.model;

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

/**
 * The {@code QRgenerator} class is specialized for creating QR Codes from {@code String}. Writes output to {@code Path} in <i>.png</i> format.
 * <p>
 * Has no Constructor nor fields, only {@link QRgenerator#createQR} method.
 * </p>
 */
public class QRgenerator {

    /** 
    * Creates QR Code from {@code String}.
    * @param data      - information which will be contained in output
    * @param path      - path to file where output will be stored
    * @param charset   - {@linkplain java.nio.charset.Charset charset} used for encoding/decoding {@code String}
    * @param hashmap   - specify {@code EncodeHintType} and {@code ErrorCorrectionLevel}
    * @param height    - height of output image
    * @param width     - width of output image
    */
    public static void createQR(String data, String path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width) throws  IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        
        MatrixToImageWriter.writeToPath(matrix, path.substring(path.lastIndexOf('.') + 1), Path.of(path));
    }
}
