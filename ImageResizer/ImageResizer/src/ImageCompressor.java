import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class ImageCompressor implements Runnable {
    private static final int TARGET_WIDTH = 300;
    private static final Object HINT = RenderingHints.VALUE_INTERPOLATION_BILINEAR;;
    private static final boolean HIGHER_QUALITY = true;
    private String dstFolder;
    private long start;
    private Queue<File> queue;

    public ImageCompressor(Queue<File> queue, String dstFolder, long start){
        this.queue = queue;
        this.dstFolder = dstFolder;
        this.start = start;
        }

    @Override
    public void run() {
        try {
            while (true) {
                File ob = queue.poll();
                if (ob != null) {
                    writeImage(ob);
                }
                if(ob == null){
                  break;
                }
                writeImage(ob);
            }
        }catch (IOException ex) {
                ex.printStackTrace();
            }
        System.out.println(Thread.currentThread().getName() + " - " + "Finished after start " + (System.currentTimeMillis() - start) + " ms");;
    }

    public void writeImage(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        int targetHeight = (int) Math.round(img.getHeight() / (img.getWidth() / (double) TARGET_WIDTH));
        File newFile = new File(dstFolder + "/" + file.getName());
        ImageIO.write(Resizer.getScaledInstance(img, TARGET_WIDTH, targetHeight, HINT, HIGHER_QUALITY), "jpg", newFile);
        System.out.println(Thread.currentThread().getName() + " - " + newFile.getAbsolutePath());
    }
}

