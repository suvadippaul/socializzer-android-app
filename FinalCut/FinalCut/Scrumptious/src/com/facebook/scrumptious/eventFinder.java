package com.facebook.scrumptious;

/**
 * Created by shreyas on 22/4/15.
 */

public class eventFinder {

    public userPreferences user;
    public Event[] choices;
    int size=0;

    public void eventFinder(userPreferences u, Event[] c, int s)
    {
        user=u;
        choices=c;
        size=s;
    }

    private void swap(int a, int b)
    {
        Event temp;
        temp=choices[a];
        choices[a]=choices[b];
        choices[b]=temp;
    }

    private double matching(Event check)
    {
        //TODO implement matching alogirithm
        return 1.0;
    }

    private void sort(int start, int end)
    {
        if(end<=start)
        {
            return;
        }
        int pivot=((end-start)/2)+start;
        swap(pivot,end);
        int i;
        int hold=start;
        for(i=start;i<end;i++)
        {
            if(matching(choices[i])>matching(choices[end]))
            {
                swap(i,hold++);
            }
        }
        swap(end,hold);
        sort(start,hold-1);
        sort(hold+1,end);
    }

    private void shift()
    {
        for(int i=0;i<size-1;i++)
        {
            choices[i]=choices[i+1];
        }
        size--;
    }

    public Event bestChoice()
    {
        sort(0,size-1);
        Event use=choices[0];
        shift();
        return use;
    }

}
