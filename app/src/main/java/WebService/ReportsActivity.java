package WebService;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.RetrieveOpenDocuments;
import Model.RetrieveReports;

public class ReportsActivity {

    Activity activity;

    public ReportsActivity(Activity activity) {
        this.activity = activity;
    }


    public void RetrieveReports(RecyclerView rv_reports) {

        List<RetrieveReports> listOfItems = new ArrayList<>();
        Adapter.RetrieveReports adapter = new Adapter.RetrieveReports(activity, listOfItems);


        rv_reports.setAdapter(adapter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.RETRIEVEREPORTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray datas = new JSONArray(response);

                    for (int i = 0; i<datas.length(); i++)
                    {

                        JSONObject dataObject = datas.getJSONObject(i);
                        String transNo = dataObject.getString("TransNo");
                        String dateAdded = dataObject.getString("DateAdded");
                        String image = dataObject.getString("Picture");



                        Model.RetrieveReports model = new Model.RetrieveReports(transNo,dateAdded,image);
                        listOfItems.add(model);


                    }


                } catch (Exception e) {

                }

                Adapter.RetrieveReports adapter = new Adapter.RetrieveReports(activity,listOfItems);
                rv_reports.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();



                return params;
            }
        };
        retryPolicy(stringRequest);
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void retryPolicy(StringRequest stringRequest)
    {
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
