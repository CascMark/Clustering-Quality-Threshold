package data;

import java.io.Serializable;

abstract class Attribute implements Serializable {

    protected String name;
    protected int index;

    Attribute(String name, int index){
        this.name = name;
        this.index = index;
    }

    protected String getName(){ return name; }

    protected int getIndex(){ return index; }

    @Override
    public String toString(){ return name; }

}