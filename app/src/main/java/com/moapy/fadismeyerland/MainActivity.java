package com.moapy.fadismeyerland;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner menuSpinner;
    public GridView gridView;
    public short category;
    public MenuAdapter menuAdapter;
    public JSONArray productArray;
    private ArrayList<FoodItem> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initFloatingActionButton();
        initGridView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        category = 0; // bring all the available categories
        refreshMenu();
    }

    private void refreshMenu() {
        menuAdapter.clear();
        menuFilter();
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                try {
                    startActivity(intent);

                } catch (Exception e) {
                    Log.e("Main", "checkout", e);
                }
            }
        });
    }

    private void initGridView() {
        gridView = (GridView)findViewById(R.id.gridview);
        menuAdapter = new MenuAdapter(this);
        gridView.setAdapter(menuAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try {
                    if(category == 0) {
                        category = (short)(position+1);
                        menuSpinner.setSelection(position+1);
                        refreshMenu();
                    }
                    else {
                        // To do : food detail
                    }

                } catch (Exception e) {
                    Log.e("menuAdapter", "exception!", e);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //android.view.MenuItem item = menu.findItem(food_spinner);
        //menuSpinner = (Spinner) MenuItemCompat.getActionView(item);
        menuSpinner = (Spinner) findViewById(R.id.food_spinner);

        //TextView tv = (TextView) menuSpinner.getRootView();
        //tv.setTypeface(null, Typeface.BOLD); //to make text bold
        //tv.setTypeface(Typeface.DEFAULT); //to make text normal

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.food_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuSpinner.setAdapter(adapter);
        //TextView tv = (TextView) menuSpinner.getSelectedView();
        //menuSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                category = (short)position;
                refreshMenu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(android.view.MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_checkout) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void menuFilter() {
        String menuURL = (category == 0) ? Config.CATEGORY_ALL_URL : Config.ITEMS_BY_CATEGORY_URL;
        StringRequest postRequest = new StringRequest(Request.Method.POST, menuURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("")) {
                            try {
                                parseRecords(new JSONArray(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Unable to fetch", "yes");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("category", String.valueOf(category));
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        postRequest.setShouldCache(false);
        CustomVolleyRequest.getInstance(this).getRequestQueue().getCache().clear();
        CustomVolleyRequest.getInstance(this).getRequestQueue().add(postRequest);
    }

    private void parseRecords(JSONArray newArray) throws JSONException {
        records.clear();
        if(category == 0) {
            for (int i = 0; i < newArray.length(); i++) {
                String c_id = newArray.getJSONObject(i).getString("cid");
                String c_name = newArray.getJSONObject(i).getString("cname");
                String c_type = newArray.getJSONObject(i).getString("ctype");
                String c_image = newArray.getJSONObject(i).getString("cimage");
                records.add(new FoodItem(c_id, c_name, c_type, Config.IMAGE_PATH_URL + c_image.substring(0, c_image.lastIndexOf('.'))));
            }
        }
        else {
            for (int i = 0; i < newArray.length(); i++) {
                String f_id = newArray.getJSONObject(i).getString("id");
                String f_name = newArray.getJSONObject(i).getString("name");
                String f_price = newArray.getJSONObject(i).getString("price");
                String f_category = newArray.getJSONObject(i).getString("category");
                String f_image = newArray.getJSONObject(i).getString("imagePath");
                records.add(new FoodItem(f_id, f_name, f_price, f_category, Config.IMAGE_PATH_URL + f_image.substring(0, f_image.lastIndexOf('.'))));
            }
        }

        menuAdapter.addAll(records);
        menuAdapter.notifyDataSetChanged();
    }

}
