import java.awt.*;
import java.awt.image.BufferedImage;

public class Resizer {
    public static BufferedImage getScaledInstance (BufferedImage img,
                                                   int targetWidth,
                                                   int targetHeight,
                                                   Object hint,
                                                   boolean higherQuality) {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        int width = higherQuality ? img.getWidth() : targetWidth;
        int height = higherQuality ? img.getHeight() : targetHeight;
        do {
            if (higherQuality && width > targetWidth) {
                width /= 2;
                if (width < targetWidth) {
                    width = targetWidth;
                }
            }
            if (higherQuality && height > targetHeight) {
                height /= 2;
                if (height < targetHeight) {
                    height = targetHeight;
                }
            }
            BufferedImage tmp = new BufferedImage(width, height, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, width, height, null);
            g2.dispose();
            ret = tmp;
        }
            while (width != targetWidth || height != targetHeight);
                return ret;
            }
        }





