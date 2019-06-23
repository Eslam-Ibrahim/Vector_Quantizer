/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vector_quantizer;

import java.util.ArrayList;
import java.util.Formatter;

/**
 *
 * @author Eslam Ibrahim
 */
public class writeFile {
    
    private Formatter format;
    
    public void openFile(String fileName)
    {
        try
        {
         format = new Formatter(fileName);
        }
        catch (Exception e)
        {
            
        }
        
    }
    public void writeString(String str)
    {
        format.format("%s%s", str," ");
    }
    public void writeArrayList(ArrayList<Integer> list)
    {
        for (int i = 0; i < list.size(); i++) {
            format.format("%s%s", list.get(i).toString()," ");
        }
    }
   
    public void closeFile()
    {
      format.close();
    }
    
}