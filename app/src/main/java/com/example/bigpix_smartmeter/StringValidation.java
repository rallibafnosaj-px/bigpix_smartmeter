package com.example.bigpix_smartmeter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidation {

    public boolean HasSpecialCharacters(String text)
    {
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(text);

        if(hasSpecial.find())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean HasNull(String text)
    {
        if(text.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }



}
