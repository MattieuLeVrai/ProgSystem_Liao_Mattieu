import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;

public class Image {
    private int width;
    private int height;
    // pixels[y][x][0=R,1=G,2=B]
    private int[][][] pixels; // pixels[y][x][0=R,1=G,2=B]

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide.
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
    }

    /**
     * Définit la couleur d'un pixel à la position (x, y)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3)
     */
    public void save_txt(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        int r, 
            g, 
            b;
        
        writer.write("P3\n");
        writer.write(this.width + " " + this.height +"\n");
        writer.write("255\n");
        
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                r = pixels[y][x][0];
                g = pixels[y][x][1];
                b = pixels[y][x][2];
                writer.write(r + " " + g + " " + b + " ");
            }
            writer.write("\n");
        }
        writer.close();

    }


    public void save_binaire(String filename) throws IOException {
        FileOutputStream writer = new FileOutputStream(filename);
        writer.write("P6\n".getBytes());
        writer.write((this.width + " " + this.height +"\n").getBytes());
        writer.write("255\n".getBytes());

        byte[] tableau = new byte[3];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {

                tableau[0] = (byte) pixels[y][x][0];
                tableau[1] = (byte) pixels[y][x][1];
                tableau[2] = (byte) pixels[y][x][2];

                writer.write(tableau);
            }
        }

        writer.close();
    }
    
}