package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigpix_smartmeter.R;

import java.util.List;



public class RetrieveOpenDocuments extends RecyclerView.Adapter<RetrieveOpenDocuments.GetViewHolder> {


    Context context;
    List<Model.RetrieveOpenDocuments> listOfItems;
    Activity activity;


    public RetrieveOpenDocuments(Context context, List<Model.RetrieveOpenDocuments> listOfItems, Activity activity) {
        this.context = context;
        this.listOfItems = listOfItems;
        this.activity = activity;
    }


    @NonNull
    @Override
    public RetrieveOpenDocuments.GetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.retrieveopendocuments, parent, false);
        Animation animation1 =
                AnimationUtils.loadAnimation(view.getContext(),
                        R.anim.animation);
        view.startAnimation(animation1);

        return new GetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetrieveOpenDocuments.GetViewHolder holder, int position) {

        Model.RetrieveOpenDocuments list = listOfItems.get(position);

        holder.tv_dueDate.setText(list.getDueDate());
        holder.tv_transNo.setText(list.getTransNo());
        holder.tv_position.setText("" + (position + 1));


        // onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionDialog(holder.itemView, list.getUnitPrice(), list.getTransNo(), "" + (position + 1),list.getId());
            }
        });


    }

    private void TransactionDialog(View view, String amountDue, String transNo, String position, String documentID) {

        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        View viewinflater = layoutInflater.inflate(R.layout.transaction, null);
        AlertDialog alertDialog = new AlertDialog.Builder(viewinflater.getContext(),
                R.style.CustomAlertDialog).setView(viewinflater).show();

        TextView tv_amountDue = viewinflater.findViewById(R.id.tv_amountDue);
        TextView tv_collection = viewinflater.findViewById(R.id.tv_position);
        TextView tv_transNo = viewinflater.findViewById(R.id.tv_transNo);
        EditText et_amountRendered = viewinflater.findViewById(R.id.et_amountRendered);
        Button btn_submit = viewinflater.findViewById(R.id.btn_submit);
        ImageView iv_loading = viewinflater.findViewById(R.id.iv_loading);

        tv_amountDue.setText(tv_amountDue.getText().toString() + " " + amountDue);
        tv_collection.setText(tv_collection.getText().toString() + " " + position);
        tv_transNo.setText(tv_transNo.getText().toString() + " " + transNo);
        et_amountRendered.setText(amountDue);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessTransaction(iv_loading,viewinflater.getContext(),transNo,documentID,amountDue,alertDialog);
            }
        });


    }

    private void ProcessTransaction(ImageView iv_loading, Context context,String transNo, String documentID, String amountDue, AlertDialog alertDialog) {

        iv_loading.setVisibility(View.VISIBLE);
        WebService.Dashboard onlineDB = new WebService.Dashboard(context, activity);
        onlineDB.ProcessTransaction(documentID,amountDue,transNo,iv_loading,alertDialog);

    }

    @Override
    public int getItemCount() {

        return listOfItems.size();
    }


    public class GetViewHolder extends RecyclerView.ViewHolder {

        TextView tv_position, tv_transNo, tv_dueDate;

        public GetViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_position = itemView.findViewById(R.id.tv_position);
            tv_transNo = itemView.findViewById(R.id.tv_transNo);
            tv_dueDate = itemView.findViewById(R.id.tv_dueDate);


        }
    }
}
