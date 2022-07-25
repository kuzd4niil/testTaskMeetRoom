package org.kuzd4niil.testTaskMeetRoom.services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : daniil
 * @description :
 * @create : 2022-07-25
 */
@Service
public class AvatarCreationService {
    private MessageDigest md5;

    public AvatarCreationService() throws NoSuchAlgorithmException {
        this.md5 = MessageDigest.getInstance("MD5");
    }

    private BufferedImage paintOnImage(short[] imgBitMap) {
        BufferedImage img = new BufferedImage(480, 480, BufferedImage.TYPE_INT_ARGB);

        byte[] bytes = md5.digest();

        int colorCode = ((int)bytes[0] & 0xFF)
                | (((int)bytes[1] & 0xFF) << 8)
                | (((int)bytes[2] & 0xFF) << 16)
                | (((int)bytes[3] & 0xFF) << 24);
        Color color = new Color(colorCode, false);

        Graphics2D g2d = (Graphics2D) img.getGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 480, 480);

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                boolean whichColor = (imgBitMap[i] & (1 << j)) > 0;
                if (whichColor) {
                    g2d.setColor(color);
                } else {
                    g2d.setColor(Color.WHITE);
                }

                g2d.fillRect(j * 30, i * 30, j * 30 + 30, i * 30 + 30);
            }
        }

        return img;
    }

    private short[] getBitMap(String username) throws NoSuchAlgorithmException {
        md5 = MessageDigest.getInstance("MD5");
        md5.update(username.getBytes());

        byte[] leftSideImage = md5.digest();
        short[] imageBitMap = new short[16];

        for (int i = 0; i < 16; ++i) {
            imageBitMap[i] = (short)(leftSideImage[i] | (short)(Integer.reverse(leftSideImage[i]) >> 16));
        }

        return imageBitMap;
    }

    public byte[] genAvatar(String username) throws NoSuchAlgorithmException, IOException {
        short[] imageBitMap = getBitMap(username);
        BufferedImage img = paintOnImage(imageBitMap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);

        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
