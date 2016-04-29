package com.example.a;


import android.content.Context;  
import android.content.res.TypedArray;  
import android.graphics.Bitmap;  
import android.graphics.Bitmap.Config;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.NinePatch;
import android.graphics.Paint;  
import android.graphics.PorterDuff;  
import android.graphics.PorterDuffXfermode;  
import android.graphics.Rect;
import android.util.AttributeSet;  
import android.widget.ImageView;

public class ImageMask extends ImageView {
    int mImageSource = 0;
    int mMaskSource = 0;
    RuntimeException mException;
	 public ImageMask(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MaskImage, 0, 0);
	    mImageSource = a.getResourceId(R.styleable.MaskImage_image, 0);
	    mMaskSource  = a.getResourceId(R.styleable.MaskImage_mask, 0);
	    if(mImageSource ==0 || mMaskSource ==0){
            mException = new IllegalArgumentException(a.getPositionDescription() + 
                    "��ʾͼƬ������ͼƬδ����");
          }

       if (mException != null){
         throw mException;
	    }
	    Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), mImageSource);
	    Bitmap  maskmap = BitmapFactory.decodeResource(getResources(), mMaskSource);
	    Bitmap  newBitmap  = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Config.ARGB_8888);
	    Canvas  canvas = new Canvas(newBitmap);
	    NinePatch path = new NinePatch(maskmap, maskmap.getNinePatchChunk(), null);
	    path.draw(canvas, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),null);
	    Bitmap  result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	    Canvas  canvas2 = new Canvas(result);
	    Paint   paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	    canvas2.drawBitmap(bitmap, 0, 0, null);
	    canvas2.drawBitmap(newBitmap, 0, 0,paint);
	    paint.setXfermode(null);
	    setImageBitmap(result); 
	    setScaleType(ScaleType.CENTER);   
        a.recycle();
        bitmap.recycle();
        maskmap.recycle();
        newBitmap.recycle();
	    
	}

	
}