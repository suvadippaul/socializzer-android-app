package com.facebook.scrumptious;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class DisplayEvent extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double rating=0;
        double averageCostFor2=0;
        int picture=1;
        String name="default name";
        String description="default description";
        int subtype=0;
        int type=0;
        double latitude=0;
        double longitude=0;
        Bundle receive=this.getIntent().getBundleExtra("event");
        if(receive!=null)
        {
            rating=receive.getDouble("rating");
            averageCostFor2=receive.getDouble("averageCostFor2");
            picture=receive.getInt("picture");
            name=receive.getString("name");
            description=receive.getString("description");
            type=receive.getInt("type");
            subtype=receive.getInt("subtype");
            latitude=receive.getDouble("latitude");
            longitude=receive.getDouble("longitude");
        }

        setContentView(R.layout.activity_display_event);
        TextView title=(TextView)findViewById(R.id.title_text);
        ImageView image=(ImageView)findViewById(R.id.event_image);
        TextView rate=(TextView)findViewById(R.id.rating);
        TextView typ=(TextView)findViewById(R.id.type);
        TextView cos=(TextView)findViewById(R.id.cost);
        TextView desc=(TextView)findViewById(R.id.desc_text);
        try
        {
            Log.d("display","enter");
            title.setText(name);
            Log.d("display", name);
            desc.setText(description);
            Log.d("display", description);
            rate.setText("Rating : "+Double.toString(rating));
            Log.d("display",Double.toString(rating));
            String[] types = DisplayEvent.this.getResources().getStringArray(R.array.food_types);
            if(type==0)
            {
                String[] subtypes=DisplayEvent.this.getResources().getStringArray(R.array.sub_types1);
                typ.setText(types[type]+" : "+subtypes[subtype]);
            }
            if(type==1)
            {
                String[] subtypes=DisplayEvent.this.getResources().getStringArray(R.array.sub_types2);
                typ.setText(types[type]+" : "+subtypes[subtype]);
            }
            if(type==2)
            {
                String[] subtypes=DisplayEvent.this.getResources().getStringArray(R.array.sub_types3);
                typ.setText(types[type]+" : "+subtypes[subtype]);
            }
            if(type==3)
            {
                String[] subtypes=DisplayEvent.this.getResources().getStringArray(R.array.sub_types4);
                typ.setText(types[type]+" : "+subtypes[subtype]);
            }
            if(type==4)
            {
                String[] subtypes=DisplayEvent.this.getResources().getStringArray(R.array.sub_types5);
                typ.setText(types[type]+" : "+subtypes[subtype]);
            }

            Log.d("display", "sample type");
            cos.setText("Cost for 2: " + Double.toString(averageCostFor2));
            Log.d("display", Double.toString(averageCostFor2));
            picture=picture/3;
            if(picture==0)
            {
                image.setImageResource(R.drawable.restaurant);
            }
            if(picture==1)
            {
                image.setImageResource(R.drawable.club);
            }
            if(picture==2)
            {
                image.setImageResource(R.drawable.movie);
            }
            if(picture==3)
            {
                image.setImageResource(R.drawable.sport);
            }
            if(picture==4)
            {
                image.setImageResource(R.drawable.chilling);
            }
            /*Resources resources = getResources();
            image.setImageDrawable(resources.getDrawable(R.drawable.add_food));*/
            Log.d("display","done");
        }
        catch (Exception e)
        {
            Log.d("display","error");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
