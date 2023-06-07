package com.fatweb.allergysafenz.Graphics;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class Bmp {

    public static Bitmap decodeSampledBitmapFromResource(String resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId, options);
    }

    public static Matrix rotateMatrix(String imageurl) {
        Matrix matrix = new Matrix();
        matrix.postRotate(getImageOrientation(imageurl));
        return matrix;
    }

    public static String getPath(Uri uri, Context context) {
        String res;
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        res = cursor.getString(idx);
        cursor.close();
        return res;
    }

    public static String getSelectedPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
//        Cursor cursor = activity
//                .managedQuery(uri, projection, null, null, null);
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap cutImage(Bitmap source) {
        Bitmap result;
        if (source.getWidth() >= source.getHeight()) {

            result = Bitmap.createBitmap(source,
                    source.getWidth() / 2 - source.getHeight() / 2, 0,
                    source.getHeight(), source.getHeight());

        } else {

            result = Bitmap.createBitmap(source, 0, source.getHeight() / 2
                            - source.getWidth() / 2, source.getWidth(),
                    source.getWidth());
        }
        return result;
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

        photo = Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public static Bitmap resizePhoto(Bitmap bitmap) {
        Bitmap dstBmp;
        if (bitmap.getWidth() >= bitmap.getHeight()) {

            dstBmp = Bitmap.createBitmap(bitmap,
                    bitmap.getWidth() / 2 - bitmap.getHeight() / 2, 0,
                    bitmap.getHeight(), bitmap.getHeight());

        } else {

            dstBmp = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2
                            - bitmap.getWidth() / 2, bitmap.getWidth(),
                    bitmap.getWidth());
        }

        return dstBmp;
    }

    public static Bitmap save(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap mBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        v.draw(canvas);
        return mBitmap;
    }

    private static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

}
