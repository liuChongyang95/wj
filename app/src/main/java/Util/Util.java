package Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Util {

    public static byte[]  getPicture(Drawable drawable) throws IOException {
        if (drawable == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] ba = os.toByteArray();
        os.close();
        return ba;
    }

    public static Drawable getPicture(byte[] drawable) throws IOException{
        Drawable photo;
        Bitmap bitmap = BitmapFactory.decodeByteArray(drawable, 0, drawable.length, null);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        photo = bitmapDrawable;
        return photo;
    }
}
