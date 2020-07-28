package com.vicenteaguilera.mylock.utility;

import android.util.Log;

public class StringHelper
{
    //ahhahah   @ ibm.com.mx
    //jhasjadja @ gmail.com

    private String[] parts;

    public boolean isEmail(String email)
    {
        return hasArroba(email);
    }
    private boolean hasArroba(String email)
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
            /*Log.e("tag",parts[0]+"");
            return true;*/
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
    }

    public boolean isNotEmptyCredentials(String email,String password)
    {
        return isEmail(email) && !password.isEmpty();
    }

}
