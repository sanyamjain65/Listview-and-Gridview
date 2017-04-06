package mypocketvakil.example.com.lab;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG=MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private static  String url="http://api.androidhive.info/contacts/";
    Button button;
    ListView listview;
    GridView gridview;
    ArrayList<HashMap<String,String>> contactlist;
    ArrayList<String> gridlist;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        listview=(ListView) findViewById(R.id.listview);
        gridview=(GridView)findViewById(R.id.grid_view);
        contactlist =new ArrayList<>();
        gridview.setVisibility(View.GONE);
        new getContacts().execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listview.setVisibility(View.GONE);
                gridview.setVisibility(View.VISIBLE);
                new newContacts().execute();
            }
        });


    }
    private class newContacts extends AsyncTask<Void,Void,Void>
    {
        String name1[]=new String[20];
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh=new HttpHandler();
            String jsonStr= sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if(jsonStr!=null)
            {
                try {
                    JSONObject object=new JSONObject(jsonStr);
                    JSONArray contacts = object.getJSONArray("contacts");
                    for (int i=0;i<contacts.length(); i++)
                    {
                        JSONObject c=contacts.getJSONObject(i);

                        String name = c.getString("name");



                        name1[i]=name;



//                        HashMap<String,String> contact=new HashMap<>();
//                        contact.put("name", name);
//
//                        contactlist.add(contact);
//

                    }


                }catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Json nai mila",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }

            gridview.setAdapter(new TextViewAdapter(MainActivity.this,name1));
        }
    }
    private class getContacts extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh=new HttpHandler();
            String jsonStr= sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if(jsonStr!=null)
            {
                try {
                    JSONObject object=new JSONObject(jsonStr);
                    JSONArray contacts = object.getJSONArray("contacts");
                    for (int i=0;i<contacts.length(); i++)
                    {
                        JSONObject c=contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");


                        HashMap<String,String> contact=new HashMap<>();
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);
                        contactlist.add(contact);


                    }

                }catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Json nai mila",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactlist,
                    R.layout.list_item, new String[]{"name", "email",
                    "mobile"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});

            listview.setAdapter(adapter);
        }
    }

}
