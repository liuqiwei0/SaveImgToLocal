package com.example.csdc.imagelocalsavetest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by csdc on 2019/1/26.
 */

public class ImgUtils {

    public static boolean saveImgToGallery(Context context , Bitmap bitmap){
        try{
                String  path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "lqw";
                File fileDir = new File(path);
                if(!fileDir.exists()){
                    fileDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(fileDir+fileName);
                file.createNewFile();

                FileOutputStream fos = new FileOutputStream(file);
                boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG,60,fos);
                fos.flush();
                fos.close();

                Uri uri = Uri.fromFile(file);
                //发送广播通知系统去扫描更新图库
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
                if(isSuccess){
                    return true;
                }else {
                    return false;
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
