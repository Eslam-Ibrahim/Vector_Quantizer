/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vector_quantizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/**
 *
 * @author Eslam Ibrahim
 */
public class ImgRW {
    
    public  int width;
    public  int height;

    public ImgRW() {
//        width=0;
  //      height=0;
    }

    public ImgRW(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    
    public  ArrayList <Integer> readImage(String filePath)
    {
        File file=new File(filePath);
        BufferedImage image=null;
        try
        {
            image=ImageIO.read(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

          width=image.getWidth();
          height=image.getHeight();
        int[][] pixels=new int[height][width];

        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                int rgb=image.getRGB(x, y);
                int alpha=(rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;

                pixels[y][x]=r;
            }
        }

         ArrayList <Integer> list = new ArrayList<>();
        
        for (int i = 0; i <width ; i++) {
            
            for (int j = 0; j < height; j++) 
                list.add(pixels[i][j]);
                
            }
        return list;
    }

    public  void writeImage(ArrayList<Integer> pixel,String outputFilePath)
    {
        int[][] pixels=new int[height][width];
        // convert to 2D
        int counter = 0;
        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                

                pixels[x][y] = pixel.get(counter);
                counter++;
            }
        }
        
        File fileout=new File(outputFilePath);
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );

        for(int x=0;x<width ;x++)
        {
            for(int y=0;y<height;y++)
            {
                image2.setRGB(x,y,(pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]));
            }
        }
        try
        {
            ImageIO.write(image2, "jpg", fileout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
