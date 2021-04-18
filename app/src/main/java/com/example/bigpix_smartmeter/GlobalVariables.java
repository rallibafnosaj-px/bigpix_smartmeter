package com.example.bigpix_smartmeter;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {


    public static boolean isAttachment1 = false;
    public static boolean isAttachment2 = false;
    public static boolean isAttachment3 = false;


    public static List<String> listOfAttachments = new ArrayList<String>();

    public static Uri outputFileUri;
    public static boolean thread = false;
    public static int processList = 0;
    public static int imageCounter = 0;
}
