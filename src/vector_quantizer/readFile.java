/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vector_quantizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Eslam Ibrahim
 */
public class readFile {
    
    private Scanner scan;
    
    public void openFile(String fileName)
    {
        try
        {
        scan = new Scanner (new File(fileName));
        }
        catch (Exception e)
        {
            
        }
        
    }
    public String readfile()
    {
        String contents="";
        while(scan.hasNextLine())
        {
            contents+=scan.nextLine();
        }
        return contents;
    }
    public ArrayList<Integer> readArrayList()
    {
        ArrayList<Integer> output = new ArrayList<>(0);
        while(scan.hasNext())
        {
            String content = scan.next();
           output.add(Integer.parseInt(content));
        }
        return output;
    }
    public void closeFile()
    {
        scan.close();
    }
}
