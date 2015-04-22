package com.facebook.scrumptious;

/**
 * Created by shreyas on 21/4/15.
 */

import java.io.Serializable;

public class Event implements Serializable{

    public static final long SerialVersionUID=1L;
    public int picture=0;
    public String name="default name";
    public String description="default description";
    public int type=0;
    public int subtype=0;
    public float latitude=0;
    public float longitude=0;

    public String toString()
    {
        return name;
    }

}
