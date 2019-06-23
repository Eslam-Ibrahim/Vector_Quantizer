/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vector_quantizer;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Eslam Ibrahim
 */
public class Vector {
    
   private ArrayList<Integer> list;

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    public Vector(ArrayList<Integer> list) {
       // list = new ArrayList<Integer>();
        this.list = list;
    }

    public Vector ()
    {
        list = new ArrayList<Integer>();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return true;
    }
    
}
