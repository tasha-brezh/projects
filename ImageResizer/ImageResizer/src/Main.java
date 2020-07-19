import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    private static final String SRC_FOLDER = "/Users/User/Desktop/photo";
    private static final String DST_FOLDER = "/Users/User/Desktop/dst";
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {
        File srcDir = new File(SRC_FOLDER);
        File[] files = srcDir.listFiles();
        if (files == null) {
            System.out.println("папка пустая");
            return;
        }
        long start = System.currentTimeMillis();
        Queue<File> queue = new ConcurrentLinkedQueue<>(Arrays.asList(files));
        ImageCompressor imageCompressor = new ImageCompressor(queue, DST_FOLDER, start);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < AVAILABLE_PROCESSORS; i++) {
            threads.add(new Thread(imageCompressor));
        }
        for(Thread thread : threads) {
            thread.start();
        }
        for(Thread thread : threads) {
            thread.join();
        }
            System.out.println("Все потоки завершили свою работу");
        }
    }