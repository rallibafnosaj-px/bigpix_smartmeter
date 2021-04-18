package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigpix_smartmeter.GlobalVariables;
import com.example.bigpix_smartmeter.R;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import WebService.DashboardActivity;


public class RetrieveOpenDocuments extends RecyclerView.Adapter<RetrieveOpenDocuments.GetViewHolder> {


    Context context;
    List<Model.RetrieveOpenDocuments> listOfItems;
    Activity activity;
    private final int IMG_REQUEST = 2;
    ImageView iv_loading;
    ProgressDialog  progressDialog;


    public static CheckBox cb_attachment1;
    public static CheckBox cb_attachment2;
    public static CheckBox cb_attachment3;


    Runnable runnable;
    Handler handler;

    public RetrieveOpenDocuments(Context context, List<Model.RetrieveOpenDocuments> listOfItems, Activity activity) {
        this.context = context;
        this.listOfItems = listOfItems;
        this.activity = activity;
    }


    @NonNull
    @Override
    public RetrieveOpenDocuments.GetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_opendocuments, parent, false);
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
                TransactionDialog(holder.itemView, list.getUnitPrice(), list.getTransNo(), "" + (position + 1), list.getId());
            }
        });


    }

    private void TransactionDialog(View view, String amountDue, String transNo, String position, String documentID) {

        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        View viewinflater = layoutInflater.inflate(R.layout.dialog_transaction, null);
        AlertDialog alertDialog = new AlertDialog.Builder(viewinflater.getContext(),
                R.style.CustomAlertDialog).setView(viewinflater).show();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                GlobalVariables.listOfAttachments.clear();
            }
        });

        TextView tv_amountDue = viewinflater.findViewById(R.id.tv_amountDue);
        TextView tv_collection = viewinflater.findViewById(R.id.tv_position);
        TextView tv_transNo = viewinflater.findViewById(R.id.tv_transNo);
        EditText et_amountRendered = viewinflater.findViewById(R.id.et_amountRendered);
        Button btn_submit = viewinflater.findViewById(R.id.btn_submit);
        iv_loading = viewinflater.findViewById(R.id.iv_loading);
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

                ProcessTransaction(transNo, documentID, amountDue);

            }
        });

        btn_attachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.isAttachment1 = true;
                SelectImage();

            }
        });


        btn_attachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.isAttachment2 = true;
                SelectImage();

            }
        });

        btn_attachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.isAttachment3 = true;
                SelectImage();

            }
        });
    }

    private void SelectImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {

            photoFile = createImageFile();

        } catch (IOException ex) {

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            GlobalVariables.outputFileUri = FileProvider.getUriForFile(activity.getApplicationContext(),

                    "com.example.androidx.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, GlobalVariables.outputFileUri);
            activity.startActivityForResult(takePictureIntent, IMG_REQUEST);
        }


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }


    private void ProcessTransaction(String transNo, String documentID, String amountDue) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait.");
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        DashboardActivity onlineDB = new DashboardActivity(context);

        GlobalVariables.processList = 1;


        handler = new Handler(Looper.myLooper());
        runnable = new Runnable() {
            @Override
            public void run() {

                if (GlobalVariables.thread == false) {

                    handler.postDelayed(runnable, 2000);
                    CheckSystemProcess();

                } else {
                    handler.removeCallbacks(runnable);
                    GlobalVariables.thread = false;
                    Intent intent = new Intent(context, com.example.bigpix_smartmeter.DashboardActivity.class);
                    activity.finish();
                    context.startActivity(intent);
                    GlobalVariables.processList = 0;
                    GlobalVariables.imageCounter = 0;
                    GlobalVariables.listOfAttachments.clear();
                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                }

            }

            private void CheckSystemProcess() {
                if (GlobalVariables.processList == 1) {
                    onlineDB.ProcessTransaction(documentID, amountDue, transNo);
                }
                if (GlobalVariables.processList == 2) {

                    try {
                        onlineDB.UploadProofOfPayment(documentID,transNo, GlobalVariables.listOfAttachments.get(GlobalVariables.imageCounter));
                    } catch (Exception e) {

                        Activity activity = (Activity) context;

                        GlobalVariables.thread = true;
                        progressDialog.dismiss();




                    }
                }
                if (GlobalVariables.processList == 0) {

                }
            }


        };

        handler.postDelayed(runnable, 2000);
        onlineDB.ProcessTransaction(documentID, amountDue, transNo);


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
