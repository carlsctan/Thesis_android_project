package com.myapp.thesis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

public class OCRScreenActivity extends Activity{

	public static final String PACKAGE_NAME = "com.myapp.thesis";
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Mathemagic/";
	public static final String lang = "eng";
	private static final String TAG = "OCRActivity.java";
	
	//protected Button _button;
	protected EditText _field;
	protected String _path;
	protected boolean _taken;
	protected static final String PHOTO_TAKEN = "photo_taken";
	protected Bitmap bitmap;
	
	public OCRScreenActivity(){
		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
		
		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
					return;
				} 
				else {
					Log.v(TAG, "Created directory " + path + " on sdcard");
				}
			}
			else{
				Log.v(TAG, "directory " + path + " exitst on sdcard");
			}
		}
		

//		//_button = (Button) findViewById(R.id.startOcr);
//		//_button.setOnClickListener(new ButtonClickHandler());
		_path = DATA_PATH + "/ocr.jpg";		
	}
	
	public OCRScreenActivity(Bitmap bitmap){
		Intent intent = getIntent();
		this.bitmap = (Bitmap) intent.getParcelableExtra("inputValKey");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ocr_page);
		
		_field = (EditText) findViewById(R.id.ocrText);
		
		Log.v(TAG, "Before Entering creating tessdata");
		
		
		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
			try {
				byte[] buf = new byte[1024];
				int len;

				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
				OutputStream out = new FileOutputStream(DATA_PATH + "tessdata/" + lang + ".traineddata");
				
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				
				in.close();
				out.close();
				Log.v(TAG, "Copied " + lang + " traineddata");
				
			} 
			catch (IOException e) {
				Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
			}
		}
		
		Intent intent = getIntent();
		this.bitmap = (Bitmap) intent.getParcelableExtra("inputValKey");
		this.OcrMethod();

	}		
	
	public void OcrMethod(){
		
		Log.v(TAG, "Before baseApi");
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(_path, options);
 
        try {
            ExifInterface exif = new ExifInterface(_path);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
 
            Log.v("LOG_TAG", "Orient: " + exifOrientation);
 
            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
 
            Log.v("LOG_TAG", "Rotation: " + rotate);
 
            if (rotate != 0) {
 
                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
 
                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);
 
                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
                // tesseract req. ARGB_8888
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
 
        } catch (IOException e) {
            Log.e("LOG_TAG", "Rotate or coversion failed: " + e.toString());
        }/*
		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		baseApi.init(DATA_PATH, lang);
		baseApi.setImage(bitmap);
		
		String recognizedText = baseApi.getUTF8Text();
		
		baseApi.end();

		Log.v(TAG, "OCRED TEXT: " + recognizedText);

		if ( lang.equalsIgnoreCase("osd") ) {
			recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9()+-=.]+", " ");
		}
		
		recognizedText = recognizedText.trim();

		if ( recognizedText.length() != 0 ) {
			_field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
			_field.setSelection(_field.getText().toString().length());
		}*/
		
	}
}
