package WebService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bigpix_smartmeter.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.RetrieveOpenDocuments;

public class Dashboard {

    Context context;
    Activity activity;

    public Dashboard(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void RetrieveOpenDocuments(RecyclerView recyclerView, Activity activity)
    {

        List<RetrieveOpenDocuments> listOfItems = new ArrayList<>();
        Adapter.RetrieveOpenDocuments adapter = new Adapter.RetrieveOpenDocuments(context, listOfItems, activity);


        recyclerView.setAdapter(adapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.RETRIEVEOPENDOCUMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray datas = new JSONArray(response);

                    for (int i = 0; i<datas.length(); i++)
                    {

                        JSONObject dataObject = datas.getJSONObject(i);
                        String transNo = dataObject.getString("TransNo");
                        String dueDate = dataObject.getString("DueDate");
                        String unitPrice = dataObject.getString("UnitPrice");
                        String itemCode = dataObject.getString("ItemCode");
                        String id = dataObject.getString("ID");


                        Model.RetrieveOpenDocuments model = new Model.RetrieveOpenDocuments(transNo, dueDate, unitPrice, itemCode, id);
                        listOfItems.add(model);


                        Log.e("OpenDocuments: ", transNo);

                    }


                } catch (Exception e) {


                }

                Adapter.RetrieveOpenDocuments adapter = new Adapter.RetrieveOpenDocuments(context, listOfItems, activity);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RetrieveOpenDocuments: ", error.getMessage());
            }
        });
        retryPolicty(stringRequest);
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void UploadProofOfPayment(String id, String transNo, String convertedImage) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.UPLOADPROOFOFPAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UploadProofPayment: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("ID", id);
                params.put("transNo", transNo);
                params.put("picture", convertedImage);

                return params;
            }
        };
        retryPolicty(stringRequest);
        Volley.newRequestQueue(context).add(stringRequest);
    }

    public void ProcessTransaction(String id, String amountDue, String transNo, ImageView loading, AlertDialog alertDialog) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.PROCESSTRANSACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("0")) {

                    Toast.makeText(context, "Successful.", Toast.LENGTH_SHORT).show();

                    for (int a = 0; a < GlobalVariables.listOfAttachments.size(); a++) {
                        UploadProofOfPayment(id, transNo, Adapter.RetrieveOpenDocuments.bitmaptoString(GlobalVariables.listOfAttachments.get(a)));
                    }
                    GlobalVariables.listOfAttachments.clear();
                    loading.setVisibility(View.INVISIBLE);
                    alertDialog.hide();
                }
               else
               {
                   Toast.makeText(context, "Please try again.", Toast.LENGTH_SHORT).show();
               }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ProcessTransaction: ", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();


                params.put("ID", id);
                params.put("amountDue", amountDue);
                params.put("transNo", transNo);

                return params;
            }
        };
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
