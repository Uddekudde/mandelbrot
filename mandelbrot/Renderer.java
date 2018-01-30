package mandelbrot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Renderer extends JComponent {

    public void paint(Graphics g) {
        int width = getSize().width;
        int height = getSize().height;
        int[] data = new int[width * height];
        /**
         * int i = 0; for (int y = 0; y < height; y++) { int red = (y * 255) /
         * (height - 1); for (int x = 0; x < width; x++) { int green = (x * 255)
         * / (width - 1); int blue = 128; data[i++] = (red << 16) | (green << 8)
         * | blue; } }
    *
         */
        int iterations = 1000;//1500;  
        double startRe = -2;//-0.141;
        double startIm = -1.2;//0.8505;
        double offset = 0.002;//-0.0005;
        int pixel = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex current = new Complex(startRe + (x * offset), startIm + (y * offset));
                int depth = Mandelbrot.check(current, iterations);
                int[] rgb = Colors.convert(depth, iterations);
                data[pixel++] = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, data, 0, width);
        g.drawImage(image, 0, 0, this);
    }
    
    public void painter() {
        int width = 3000;
        int height = 3000;
        int[] data = new int[width * height];

        int iterations = 500;  
        double startRe = -0.141;
        double startIm = 0.8505;
        double offset = -0.00005;
        int pixel = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex current = new Complex(startRe + (x * offset), startIm + (y * offset));
                int depth = Mandelbrot.check(current, iterations);
                int[] rgb = Colors.convert(depth, iterations);
                data[pixel++] = (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, data, 0, width);
        File outputfile = new File("saved.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException ex) {
            System.out.println("could not save image");
        }
    }

    public static void main(String[] args) {
        //ColorPan mandel = new ColorPan();
        //mandel.painter();
        JFrame frame = new JFrame("ColorPan"); frame.getContentPane().add(new
        Renderer()); frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        int[] ints = {};
        System.out.println(ints.length);
        /**
        Mandelbrot brot = new Mandelbrot();
        System.out.println(brot.check(new Complex(0.29, 0), 312));
        int[] rgb = Colors.convert(230,255);
        System.out.println(rgb[0]+" "+rgb[1]+" "+rgb[2]);
        double fraction = ((double) 200 / 255) * 4;
        System.out.println(fraction);
        **/
    }
}
