package com.facebook.scrumptious;

/**
 * Created by shreyas on 22/4/15.
 */

import java.io.Serializable;

public class userPreferences implements Serializable{

    public static final long SerialVersionUID=2L;
    public String name="default_name";
    public int type=0;
    public int subtype=0;
    public float latitude=0;
    public float longitude=0;
}
