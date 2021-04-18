package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigpix_smartmeter.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class RetrieveReports extends RecyclerView.Adapter<RetrieveReports.GetViewHolder> {

    Context context;
    List<Model.RetrieveReports> list;

    public RetrieveReports(Context context, List<Model.RetrieveReports> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RetrieveReports.GetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_reports, parent, false);

        return new GetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetrieveReports.GetViewHolder holder, int position) {

        Model.RetrieveReports data = list.get(position);

        holder.tv_transNo.setText(data.getTransNo());
        holder.tv_position.setText("" + (position + 1));
        holder.tv_dateProcessed.setText(data.getDateProcessed());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                View viewinflater = layoutInflater.inflate(R.layout.dialog_photos, null);
                AlertDialog alertDialog = new AlertDialog.Builder(viewinflater.getContext(),
                        R.style.CustomAlertDialog).setView(viewinflater).show();

                TextView tv_transNo = viewinflater.findViewById(R.id.tv_transNo);

                ImageView image1 = viewinflater.findViewById(R.id.iv_image1);
                ImageView image2 = viewinflater.findViewById(R.id.iv_image2);
                ImageView image3 = viewinflater.findViewById(R.id.iv_image3);

                List<String> images = Arrays.asList(data.getImage().split(","));

                    if(images.size() == 2)
                    {
                        Picasso.with(context).load(images.get(1)).into(image1);
                    }
                    else if(images.size() == 3)
                    {
                        Picasso.with(context).load(images.get(1)).into(image1);
                        Picasso.with(context).load(images.get(2)).into(image2);
                    }
                    else if(images.size() == 4)
                    {
                        Picasso.with(context).load(images.get(1)).into(image1);
                        Picasso.with(context).load(images.get(2)).into(image2);
                        Picasso.with(context).load(images.get(3)).into(image3);
                    }

                 tv_transNo.setText(tv_transNo.getText() + data.getTransNo());

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GetViewHolder extends RecyclerView.ViewHolder {

        TextView tv_transNo;
        TextView tv_position;
        TextView tv_dateProcessed;

        public GetViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_dateProcessed = itemView.findViewById(R.id.tv_dateProcessed);
            tv_position = itemView.findViewById(R.id.tv_position);
            tv_transNo = itemView.findViewById(R.id.tv_transNo);

        }
    }
}
