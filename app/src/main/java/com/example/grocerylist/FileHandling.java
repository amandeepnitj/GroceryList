package com.example.grocerylist;

import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class FileHandling {

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grocerylist";


    public void export()
    {

        StringBuilder data = new StringBuilder();
        data.append("time,Distance");
        for(int i=0;i<5;i++)
        {
            data.append("\n"+String.valueOf(i)+","+String.valueOf(i+1));
        }

        try{
            FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/grocerylist.csv");
            out.write(data.toString().getBytes());
            out.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
