package com.facebook.scrumptious;

//import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.os.ConditionVariable;
import android.support.v4.app.Fragment;
import android.support.v4.app.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.*;


import com.facebook.scrumptious.dummy.DummyContent;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */


public class MyEvents extends Fragment implements AbsListView.OnItemClickListener, OnFragmentInteractionListener {

    private static final String SERVICE_URL = "http://10.42.0.100:8080/SocializzerJSONResponse/socializeMe/event";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "picture";
    private static final String ARG_PARAM2 = "name";
    private static final String ARG_PARAM3 = "description";
    private static final String ARG_PARAM4 = "type";
    private static final String ARG_PARAM5 = "subtype";
    private static final String ARG_PARAM6 = "latitude";
    private static final String ARG_PARAM7 = "longitude";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private String mParam3;
    private int mParam4;
    private int mParam5;
    private double mParam6;
    private double mParam7;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    public ListAdapter mAdapter;
    public ArrayList<Event> events = new ArrayList<Event>();
    public int done=0;
    public ConditionVariable mCondition=new ConditionVariable();
    Bundle saved;
    // TODO: Rename and change types of parameters
    public static MyEvents newInstance(int param1, String param2, String param3, int param4, int param5, double param6, double param7) {
        MyEvents fragment = new MyEvents();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, param4);
        args.putInt(ARG_PARAM5, param5);
        args.putDouble(ARG_PARAM6, param6);
        args.putDouble(ARG_PARAM7, param7);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEvents() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        saved=savedInstanceState;
        if(savedInstanceState!=null) {
            Log.d("calls","3");
            if(savedInstanceState.getSerializable("arr")!=null)
            {
                Log.d("calls","4");
                events=(ArrayList<Event>)savedInstanceState.getSerializable("arr");
                Log.d("calls",Integer.toString(events.size()));
            }

        }
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
            mParam5 = getArguments().getInt(ARG_PARAM5);
            mParam6 = getArguments().getDouble(ARG_PARAM6);
            mParam7 = getArguments().getDouble(ARG_PARAM7);
        }

        // TODO: Change Adapter to display your content

        //TODO get JSONArray
        Person p = (Person)MyEvents.this.getActivity().getIntent().getBundleExtra("pref").getSerializable("person");
        WebServiceTask wst = new WebServiceTask(1, MyEvents.this.getActivity(), "Getting data...");
        wst.addNameValuePair("name", p.getName());
        wst.addNameValuePair("rating",Double.toString(p.getRating()));
        wst.addNameValuePair("costMe", Double.toString(p.getCostMe()));
        wst.addNameValuePair("type", Integer.toString(p.getType()));
        wst.addNameValuePair("subtype", Integer.toString(p.getSubtype()));
        wst.addNameValuePair("latitude", Double.toString(p.getLatitude()));
        wst.addNameValuePair("longitude", Double.toString(p.getLongitude()));
        /*if(events.size()==0)
        {
            wst.execute(new String[]{SERVICE_URL});
            Log.d("hey8",Integer.toString(done));
        }*/
        //wst.execute(new String[]{SERVICE_URL});
        try
        {
            String str_result= wst.execute(new String[]{SERVICE_URL}).get();
        }
        catch (Exception e)
        {

        }
        /*synchronized (wst)
        {
            try
            {
                Log.d("file","2");
                this.wait();
            }
            catch(Exception e)
            {

            }
        }*/
        Thread set=new Thread()
        {
            public void run()
            {
                mAdapter = new ArrayAdapter<Event>(getActivity(),
                        android.R.layout.simple_list_item_1, events);
            }
        };

        Log.d("waiting","closed");
        try
        {
            synchronized (wst)
            {
                set.wait();
            }
        }
        catch (Exception e)
        {

        }
        Log.d("waiting","open2");
        set.start();
        /*try
        {
            FileInputStream fin = new FileInputStream("store.txt");
            Log.d("file", "3");
            BufferedReader inp = new BufferedReader(new InputStreamReader(fin));
            Log.d("file", "4");
            handleResponse(inp.readLine());
            Log.d("file", "5");
            inp.close();
        }
        catch (Exception e)
        {
            Log.d("file", "6");
        }*/

        //handleResponse("{\"event\":[{\"averageCostFor2\":\"499.0\",\"description\":\"Meh, Nice\",\"eventID\":\"14281\",\"latitude\":\"0.0028384339702510046\",\"longitude\":\"-0.15882711356648319\",\"name\":\"Chocolate Harbour\",\"picture\":\"2\",\"rating\":\"3.508806489360052\",\"subtype\":\"1\",\"tempSatisfactionIndex\":\"70.0178099680724\",\"type\":\"3\"},{\"averageCostFor2\":\"468.0\",\"description\":\"Brilliant, Meh\",\"eventID\":\"30456\",\"latitude\":\"-0.1433460915324625\",\"longitude\":\"0.10049360890931608\",\"name\":\"Harbour Restaurant\",\"picture\":\"3\",\"rating\":\"4.97666073741557\",\"subtype\":\"0\",\"tempSatisfactionIndex\":\"63.882787678099895\",\"type\":\"1\"},{\"averageCostFor2\":\"336.0\",\"description\":\"5-star, Me Likey\",\"eventID\":\"11830\",\"latitude\":\"0.0365228397391795\",\"longitude\":\"-0.050852658834520746\",\"name\":\"Faaso's Fantasy\",\"picture\":\"4\",\"rating\":\"4.996521664260728\",\"subtype\":\"0\",\"tempSatisfactionIndex\":\"63.58297291236858\",\"type\":\"0\"},{\"averageCostFor2\":\"465.0\",\"description\":\"So beautiful, Nice\",\"eventID\":\"47957\",\"latitude\":\"0.17540602842370875\",\"longitude\":\"0.004554564061590054\",\"name\":\"Chocolate Chinese\",\"picture\":\"7\",\"rating\":\"4.905022612210195\",\"subtype\":\"1\",\"tempSatisfactionIndex\":\"63.282959828304215\",\"type\":\"4\"},{\"averageCostFor2\":\"416.0\",\"description\":\"So beautiful, Nice\",\"eventID\":\"7537\",\"latitude\":\"0.18330276692667236\",\"longitude\":\"-0.04337395259737961\",\"name\":\"Chocolate Chinese\",\"picture\":\"3\",\"rating\":\"4.9187595710007095\",\"subtype\":\"2\",\"tempSatisfactionIndex\":\"63.08873441099118\",\"type\":\"0\"}]}");
        //Log.d("JSONArray","test");
        //Log.d("objPrint",wst.getJsString());


        //Log.d("hey7",events.get(2).getName());



        //Button a=(Button) getActivity().findViewById(R.id.bn_post);

    }

    public void handleResponse(String response)
    {
        done=1;
        if(response==null)
        {
            Log.d("hey3","oops");
        }
        try

        {
            Log.d("hey4",response);
            JSONObject jso=new JSONObject(response);
            JSONArray jsarr = new JSONArray(jso.getString("event"));
            for (int i = 0; i < jsarr.length(); i++) {
                //TODO take JSONObject and put into each event object
                try {
                    JSONObject js = jsarr.getJSONObject(i);
                    Event e = new Event();
                    e.setEventID(js.getInt("eventID"));
                    e.setTempSatisfactionIndex(js.getDouble("tempSatisfactionIndex"));
                    e.setRating(js.getDouble("rating"));
                    e.setAverageCostFor2(js.getDouble("averageCostFor2"));
                    e.setPicture(js.getInt("picture"));
                    e.setName(js.getString("name"));
                    e.setDescription(js.getString("description"));
                    e.setType(js.getInt("type"));
                    e.setSubtype(js.getInt("subtype"));
                    e.setLatitude(js.getDouble("latitude"));
                    e.setLongitude(js.getDouble("longitude"));
                    events.add(e);
                    Log.d("hey5",Integer.toString(events.size()));
                } catch (Exception e) {
                    Log.d("no", "didn't get individual object");
                }
                //Event e=new Event();
                //events.add(e);
            }

            //Log.d("hey6",events.get(2).getName());

            /*mAdapter = new ArrayAdapter<Event>(getActivity(),
                    android.R.layout.simple_list_item_1, events);*/
        }

        catch( Exception e)
        {
            Log.d("no", "didn't get object");
        }
        if(events.size()==0)
        {
            for(int i=0;i<4;i++)
            {
                Event e= new Event();
                events.add(e);
            }
        }
        //mCondition.open();
        /*if(saved!=null)
        {
            saved.putSerializable("arr",events);
            onCreate(saved);
            Log.d("calls","1");
        }
        else
        {
            Log.d("calls","2");
            Bundle saved2=new Bundle();
            saved2.putSerializable("arr",events);
            onCreate(saved2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onFragmentInteraction(String id) {
        MainActivity use = new MainActivity();
        use.showSplashFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //if (null != mListener)
        {
            //TODO call each event's fragment
            //Intent showEvent=new Intent(MyEvents.this.getActivity(), MainActivity.class);
            //startActivity(showEvent);
            //eventGetter.postData(a);
            Intent use = new Intent(MyEvents.this.getActivity(), DisplayEvent.class);
            Bundle send=new Bundle();
            send.putDouble("rating",events.get(position).getRating());
            send.putDouble("averageCostFor2",events.get(position).getAverageCostFor2());
            send.putInt("picture",events.get(position).getPicture());
            send.putString("name",events.get(position).getName());
            send.putString("description",events.get(position).getDescription());
            send.putInt("type",events.get(position).getType());
            send.putInt("subtype",events.get(position).getSubtype());
            send.putDouble("latitude",events.get(position).getLatitude());
            send.putDouble("longitude",events.get(position).getLongitude());
            use.putExtra("event",send);
            startActivity(use);
            /*EventFragment  show = new EventFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, show).commit();*/
            /*EventFragment show = new EventFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, show);
            fragmentTransaction.commit();*/
            //eventGetter.postData();
            //MainActivity use=new MainActivity();
            //use.showSplashFragment();
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

public class WebServiceTask extends AsyncTask<String, Integer, String> {

        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;
        public String jsString;

        public String getJsString()
        {
            return jsString;
        }

        private static final String TAG = "WebServiceTask";

        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 3000;

        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;

        private int taskType = GET_TASK;
        private Context mContext = null;
        private String processMessage = "Processing...";

        private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        private ProgressDialog pDlg = null;

        public WebServiceTask(int taskType, Context mContext, String processMessage) {

            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }

        public void addNameValuePair(String name, String value) {

            params.add(new BasicNameValuePair(name, value));
        }

        private void showProgressDialog() {

            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }

        @Override
        protected void onPreExecute() {

            //hideKeyboard();
            showProgressDialog();

        }

        protected String doInBackground(String... urls) {

            String url = urls[0];
            String result = "";

            HttpResponse response = doResponse(url);

            if (response == null) {
                return result;
            } else {

                try {

                    result = inputStreamToString(response.getEntity().getContent());

                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);

                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {


            handleResponse(response);
            jsString=response;
            Log.d("hey1",response);
            Log.d("hey2",jsString);
            pDlg.dismiss();
            Log.d("waiting","opening");
            //mCondition.open();
            Log.d("waiting","open");
            /*FileOutputStream outputStream;

            try {
                outputStream = MyEvents.this.getActivity().openFileOutput("store.txt", Context.MODE_PRIVATE);
                outputStream.write(response.getBytes());
                outputStream.close();
                Log.d("file","1");

            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                FileInputStream fin = new FileInputStream("store.txt");
                Log.d("file", "3");
                BufferedReader inp = new BufferedReader(new InputStreamReader(fin));
                Log.d("file", "4");
                //handleResponse(inp.readLine());
                Log.d("file", inp.readLine());
                inp.close();
            }
            catch (Exception e)
            {
                Log.d("file", "6");
            }*/
            synchronized (this)
            {
                notify();
            }


        }

        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {

            HttpParams htpp = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

            return htpp;
        }

        private HttpResponse doResponse(String url) {

            // Use our connection and data timeouts as parameters for our
            // DefaultHttpClient
            HttpClient httpclient = new DefaultHttpClient(getHttpParams());

            HttpResponse response = null;

            try {
                switch (taskType) {

                    case POST_TASK:
                        HttpPost httppost = new HttpPost(url);
                        // Add parameters
                        httppost.setEntity(new UrlEncodedFormEntity(params));

                        response = httpclient.execute(httppost);
                        break;
                    case GET_TASK:
                        HttpGet httpget = new HttpGet(url);
                        response = httpclient.execute(httpget);
                        break;
                }
            } catch (Exception e) {

                Log.e(TAG, e.getLocalizedMessage(), e);

            }

            return response;
        }

        private String inputStreamToString(InputStream is) {

            String line = "";
            StringBuilder total = new StringBuilder();

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }

            // Return full string
            return total.toString();
        }

    }



}



/**














Object receiving happens here














 */

