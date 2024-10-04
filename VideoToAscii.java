import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.Scanner;

public class VideoToAscii {
    static {
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static final String ASCII_CHARS = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,\"^`'. ";

    private static final String video = "D:\\admin\\Documents\\java\\project\\少帅\\ss.mp4";

    public static void main(String[] args) {
        if (args.length < 1 || !new java.io.File(args[0]).exists()) {
            System.out.println("File not found.");
            args = new String[]{video};//-D java.library.path=C:\Windows\System32\opencv_java4100.dll
            System.out.println(args);
            return;
        }

        String videoPath = args[0];

        VideoCapture cap = new VideoCapture(videoPath);


        if (!cap.isOpened()) {
            System.out.println("Error opening video file");
            return;
        }

        Mat frame = new Mat();

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            // 获取终端尺寸（这里简单处理，假设固定宽度和高度）
            //TODO JAVA 如何获取终端尺寸捏？
            int terminalWidth = 200; // 可以根据实际情况调整
            int terminalHeight = 120; // 可以根据实际情况调整

            while (cap.read(frame)) {
                Mat grayFrame = new Mat();
                Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

                Mat resizedFrame = new Mat();
                Imgproc.resize(grayFrame, resizedFrame, new Size(terminalWidth, terminalHeight));

                StringBuilder asciiFrame = new StringBuilder();
                for (int y = 0; y < resizedFrame.rows(); y++) {
                    for (int x = 0; x < resizedFrame.cols(); x++) {
                        double grayValue = resizedFrame.get(y, x)[0];
                        int asciiIndex = (int) ((grayValue / 255.0) * (ASCII_CHARS.length() - 1));
                        asciiFrame.append(ASCII_CHARS.charAt(asciiIndex));
                    }
                    asciiFrame.append("\n");
                }

                // 清屏（注意：在 Windows 的命令行中不起作用，需要使用特定的清屏命令）
                if (System.console() != null) {
                    //System.console().clearScreen();
                } else {
                    for (int i = 0; i < terminalHeight + 1; i++) {
                        System.out.println("\b" + new String(new char[terminalWidth]).replace("\0", " "));
                    }
                }

                System.out.print(asciiFrame.toString());

                 //暂停一段时间，以便观看 ASCII 艺术（这里使用简单的扫描器等待用户输入）
                    //System.out.print("Press Enter to continue to the next frame...");
                    scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cap.release();
        }

        //cap.release();
    }
}




