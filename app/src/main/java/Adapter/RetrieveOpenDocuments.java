package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigpix_smartmeter.GlobalVariables;
import com.example.bigpix_smartmeter.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class RetrieveOpenDocuments extends RecyclerView.Adapter<RetrieveOpenDocuments.GetViewHolder> {


    Context context;
    List<Model.RetrieveOpenDocuments> listOfItems;
    Activity activity;
    private final int IMG_REQUEST = 2;

    public static CheckBox cb_attachment1;
    public static CheckBox cb_attachment2;
    public static CheckBox cb_attachment3;


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
        Button btn_attachment1 = viewinflater.findViewById(R.id.btn_attachment1);
        Button btn_attachment2 = viewinflater.findViewById(R.id.btn_attachment2);
        Button btn_attachment3 = viewinflater.findViewById(R.id.btn_attachment3);
        cb_attachment1 = viewinflater.findViewById(R.id.cb_attachment1);
        cb_attachment2 = viewinflater.findViewById(R.id.cb_attachment2);
        cb_attachment3 = viewinflater.findViewById(R.id.cb_attachment3);


        String COUNTRY = "PH";
        String LANGUAGE = "en";
        String convertedAmountDue = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(Double.parseDouble(amountDue));


        tv_amountDue.setText(tv_amountDue.getText().toString() + " " + convertedAmountDue);
        tv_collection.setText(tv_collection.getText().toString() + " " + position);
        tv_transNo.setText(tv_transNo.getText().toString() + " " + transNo);
        et_amountRendered.setText(convertedAmountDue);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProcessTransaction(iv_loading, viewinflater.getContext(), transNo, documentID, amountDue, alertDialog);


            }
        });

        btn_attachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.isAttachment1 = true;
                selectImage();

            }
        });


        btn_attachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.isAttachment2 = true;
                selectImage();

            }
        });

        btn_attachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.isAttachment3 = true;
                selectImage();

            }
        });

    }

    private void selectImage() {


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //

        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
        File newdir = new File(dir);
        newdir.mkdirs();

        String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";

        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {

        }

        GlobalVariables.image = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, GlobalVariables.image);
        activity.startActivityForResult(cameraIntent, IMG_REQUEST);


    }

    public static String bitmaptoString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);


    }

    private void ProcessTransaction(ImageView iv_loading, Context context, String transNo, String documentID, String amountDue, AlertDialog alertDialog) {

        iv_loading.setVisibility(View.VISIBLE);
        WebService.Dashboard onlineDB = new WebService.Dashboard(context, activity);


        if (GlobalVariables.listOfAttachments.size() == 0) {
            Toast.makeText(context, "Select image for proof of payment.", Toast.LENGTH_SHORT).show();
            iv_loading.setVisibility(View.INVISIBLE);
        } else {
            onlineDB.ProcessTransaction(documentID, amountDue, transNo, iv_loading, alertDialog);

        }


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
