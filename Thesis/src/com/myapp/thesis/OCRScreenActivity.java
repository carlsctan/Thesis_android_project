package com.myapp.thesis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//import com.googlecode.tesseract.android.TessBaseAPI;



import com.googlecode.tesseract.android.TessBaseAPI;

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
import android.view.View;
import android.widget.EditText;

public class OCRScreenActivity extends Activity{

	public static final String PACKAGE_NAME = "com.myapp.thesis";
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Mathemagic/";
	public static final String lang = "eng";
	private static final String TAG = "OCRActivity.java";
	
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
		
		_path = DATA_PATH + "/ocr.jpg";		
	}
	
//	public OCRScreenActivity(Bitmap bitmap){
//		Intent intent = getIntent();
//		this.bitmap = (Bitmap) intent.getParcelableExtra("inputValKey");
//	}
	
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

	}		
	
	public void OcrMethod(){
		
		Log.v(TAG, "Before baseApi");

		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.setDebug(true);
		baseApi.init(DATA_PATH, lang);
		//baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
		baseApi.setImage(bitmap);
		
		String recognizedText = baseApi.getUTF8Text();
		
		baseApi.end();

		Log.v(TAG, "OCRED TEXT: " + recognizedText);

		if ( lang.equalsIgnoreCase("eng2") ) {
			recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
		}
		
//		recognizedText = recognizedText.trim();

		if ( recognizedText.length() != 0 ) {
			_field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
			_field.setSelection(_field.getText().toString().length());
		}
		
	}
	
	public void ImageProcess(){
		int width = this.bitmap.getWidth();
		int height = this.bitmap.getHeight();
		int pixels[] = new int [width*height];
		//this.bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		
	}
	
//	public void procResult(View v){
//		Log.v(TAG, "Click Action is okay");
//		Intent start_intent = new Intent(OCRScreenActivity.this, ResultScreenActivity.class);
//		startActivity(start_intent);
//	}
	
}
