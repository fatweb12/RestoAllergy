package com.fatweb.allergysafenz.Storage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fatweb.allergysafenz.Graphics.Bmp;
import com.fatweb.allergysafenz.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class FileData {

    public static String storeImage(Bitmap bitmap, String fileName, String FolderName, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(FolderName, Context.MODE_PRIVATE);
        File path = new File(directory, fileName);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap getImage(String fileName, String folderName, Context context) {
        ContextWrapper cw = new ContextWrapper(
                context);
        File directory = cw.getDir(folderName,
                Context.MODE_PRIVATE);
        try {
            File f = new File(directory, fileName);
            Bitmap b = BitmapFactory
                    .decodeStream(new FileInputStream(f));
            b = Bmp.cutImage(b);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (folderName.equals("signature"))
                return null;
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        }

}

}
