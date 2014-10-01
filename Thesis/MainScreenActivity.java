package com.myapp.thesis;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainScreenActivity extends ActionBarActivity {
	static final int REQUEST_IMAGE_CAPTURE = 1;
	OCRScreenActivity OCR= new OCRScreenActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		this.getSupportActionBar().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id){
			case R.id.menu_take_pic:
				takePicture();
				break;
			case R.id.menu_gallery:
				openGallery();
				break;
			case R.id.menu_ocr:
				proceedOcr();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void proceedOcr() {
		// TODO Auto-generated method stub
		
	    ImageView mImageView = (ImageView)this.findViewById(R.id.selected_image);    
	    Bitmap imageBitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
	    
		Intent start_intent = new Intent(MainScreenActivity.this, OCRScreenActivity.class);
		start_intent.putExtra("inputValKey", imageBitmap);
		startActivity(start_intent);
	}
	

	private void openGallery() {
		// TODO Auto-generated method stub
		
	}

	private void takePicture() {
		
		File file = new File(OCR._path);
		Uri outputFileUri = Uri.fromFile(file);
		final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {		
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	        startActivityForResult(takePictureIntent, 0);
	    }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0 && resultCode == RESULT_OK) {
	    	
	        //Bundle extras = data.getExtras();
	        //Bitmap imageBitmap = (Bitmap) extras.get("data");
	        ImageView mImageView = (ImageView)this.findViewById(R.id.selected_image);
	        //mImageView.setImageBitmap(imageBitmap);
	        
	        //ocr.setBitmap(imageBitmap);
	        
	        OCR._taken = true;

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;

			Bitmap bitmap = BitmapFactory.decodeFile(OCR._path, options);

			try {
				ExifInterface exif = new ExifInterface(OCR._path);
				int exifOrientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);

				//Log.v(OCR.TAG, "Orient: " + exifOrientation);

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

				//Log.v(TAG, "Rotation: " + rotate);

				if (rotate != 0) {
					int w = bitmap.getWidth();
					int h = bitmap.getHeight();

					Matrix mtx = new Matrix();
					mtx.preRotate(rotate);

					bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
				}

				bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

			} catch (IOException e) {
				//Log.e(OCRScreenActivity.TAG, "Couldn't correct orientation: " + e.toString());
			}
			
			mImageView.setImageBitmap( bitmap );
	    }
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		android.os.Debug.stopMethodTracing();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(OCRScreenActivity.PHOTO_TAKEN, OCR._taken);
	}
	
}
