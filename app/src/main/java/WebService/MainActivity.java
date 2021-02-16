package WebService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bigpix_smartmeter.Dashboard;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity {

    Context context;
    Activity activity;


    public MainActivity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    public void LoginAccount(String username, String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.LOGINACCOUNT + "?username=" + username + "&password=" + password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if(!response.equals("1"))
                    {
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i<data.length(); i++)
                            {

                                JSONObject dataObject = data.getJSONObject(i);
                                String username = dataObject.getString("username");
                                String password = dataObject.getString("password");

                            }




                            Intent intent = new Intent(context, Dashboard.class);
                            activity.finish();
                            activity.startActivity(intent);


                        } catch (Exception e)
                        {

                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        retryPolicty(stringRequest);
        Volley.newRequestQueue(context).add(stringRequest);
    }

    // retry policy for timeouterror double post issue
    public void retryPolicty(StringRequest stringRequest)
    {
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
