package com.akrog.tolometgui2.ui.views;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by gorka on 8/02/16.
 */
public class AndroidUtils {

    public static Bitmap getScreenShot(View view) {
        boolean cache = view.isDrawingCacheEnabled();
        view.setDrawingCacheEnabled(true);
        Bitmap bmpCache = view.getDrawingCache();
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        Bitmap bitmap = Bitmap.createBitmap(
                bmpCache,
                frame.left,frame.top,frame.width(),frame.height(),
                null,true
        );
        view.setDrawingCacheEnabled(cache);
        return bitmap;
    }

    public static File saveScreenShot(Bitmap bm, Bitmap.CompressFormat format, int quality, String fileName) {
        File pix;
        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO )
            pix = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        else
            pix = Environment.getExternalStorageDirectory();
        File dir = new File(pix, "Screenshots");
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(format, quality, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            return null;
        }
        return file;
    }


}
