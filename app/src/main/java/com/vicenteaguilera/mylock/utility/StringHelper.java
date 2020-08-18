package com.vicenteaguilera.mylock.utility;

import android.util.Log;

import java.util.regex.Pattern;

public class StringHelper
{
    //ahhahah   @ ibm.com.mx
    //jhasjadja @ gmail.com
    //evalua urls
    //(https|http):(\/{2})(w{3}\.)([a-z,0-9]+(\.[a-z0-9]+)*)\.([a-z]{2,})(\.([a-z]{2}))?
    //evalua emails (gente común y corriente)
    //([a-z,0-9]+((\.|_)[a-z0-9]+)*)@([a-z,0-9]+(\.[a-z0-9]+)*)\.([a-z]{2,})(\.([a-z]{2}))?
    /**
     * \n
     * \t
     * \b
     * \r
     * \"
     * \'
     * \\
     */
    //   10/01/2050  [0-9]{2}/[0-9]{2}/[0-9]{4}   \d
    private String EXREGEMAIL="([a-z,0-9]+((\\.|_)[a-z0-9]+)*)@([a-z,0-9]+(\\.[a-z0-9]+)*)\\.([a-z]{2,})(\\.([a-z]{2}))?";
    public boolean isEmail(String email)
    {
         return Pattern.matches(EXREGEMAIL, email);
        //return hasArroba(email);
    }
    public boolean isNotEmptyCredentials(String email,String password)
    {
        return isEmail(email) && !password.isEmpty();
    }

    //private String[] parts;
    /*private boolean hasArroba(String email)
    {
        if(email.contains("@"))
        {
            parts = email.split("@");
            if(parts.length==2)
            {
                return isServerValid(parts[1]);
            }
        }
        return false;
    }
    private boolean isServerValid(String servidor)
    {
        if(servidor.contains("."))
        {
            parts=servidor.split("\\.");

            if(parts.length==2)
            {
                if(parts[0].length()>=3 && parts[1].length()>=2){
                    return true;
                }
                else {
                    return false;
                }
            }
            else if(parts.length==3)
            {
                if(parts[0].length()>=3 && parts[1].length()>=2 && parts[2].length()==2)
                {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }*/


}
