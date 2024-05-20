/*
MIT License

Copyright (c) 2024 @jazdzewt

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {

        //single character on .png size
        int oneCharHeight = 8; 
        int oneCharWidth = 8; 

        //characters that are used to build the ASCII picture
        String charString = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
        //String charString = "agni. ";

        int charArrayLenght = charString.length(); 
        char[] charArray = charString.toCharArray(); 
        
        //reading the file in
       BufferedImage image = javax.imageio.ImageIO.read(new File("\\inputfile.jpg"));

       //setting scale of the ASCII picture
       double scale = 0.05;

       int newWidth = (int) (image.getWidth() * scale);
       int newHeight = (int) (image.getHeight() * scale); 

       Image tmpImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

       BufferedImage finalImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
       
       finalImage.getGraphics().drawImage(tmpImage, 0, 0 , null);

       BufferedImage imageToSave = new BufferedImage(newWidth * oneCharWidth , newHeight * oneCharHeight, BufferedImage.TYPE_INT_RGB);

       //image to save
       Graphics2D canvas = imageToSave.createGraphics(); 

       canvas.setColor(Color.black); 
       canvas.fillRect(0,0, newWidth * oneCharWidth, newHeight * oneCharHeight);


       try (FileWriter textFile = new FileWriter("\\outputfile.txt")) {
        for(int i = 0; i < newHeight; i++){
                for(int j = 0; j < newWidth; j++){

                    int color = finalImage.getRGB( j, i);
                    
                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;

                    int gray = (blue/3 + green/3 + red/3);

                    double charNum = (((double)gray/255.0)*(double)(charArrayLenght-1));
                    int character = (int) Math.floor(charNum);

                    textFile.write(charArray[character]);

                    canvas.setColor(new Color(red, green, blue)); 
                    canvas.drawString(Character.toString(charArray[character]), (j-1)*oneCharWidth, (i-1)*oneCharHeight);
                }
                textFile.write("\n");
        }
     }
     canvas.dispose(); 
     File newImage = new File ("outputfile.png");
     ImageIO.write(imageToSave, "png", newImage); 
    }
}
