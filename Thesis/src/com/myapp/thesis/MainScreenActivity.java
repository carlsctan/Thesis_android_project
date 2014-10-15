package com.myapp.thesis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainScreenActivity extends ActionBarActivity {
	public static final int REQUEST_IMAGE_CAPTURE = 100;
	public static final int REQUEST_CODE_CROP_IMAGE = 200;
	public static final int RESULT_LOAD_IMAGE = 300;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private String _root;
	private ImageView mImageView;
	OCRScreenActivity OCR= new OCRScreenActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		this.getSupportActionBar().show();
		
		_root = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
		
		mImageView = (ImageView)this.findViewById(R.id.selected_image);
		ImageView take_photo = (ImageView) this.findViewById(R.id.take_photo);
		take_photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
		ImageView gallery = (ImageView) this.findViewById(R.id.gallery);
		gallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openGallery();
			}
		});
		ImageView ocr = (ImageView) this.findViewById(R.id.ocr);
		ocr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				proceedOcr();
			}
		});
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.main, menu);
//	    return true;
//
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		switch (id){
//			case R.id.menu_take_pic:
//				takePicture();
//				break;
//			case R.id.menu_gallery:
//				openGallery();
//				break;
//			case R.id.menu_ocr:
//				proceedOcr();
//				break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
//	private Uri getImageUri(Context inContext, Bitmap inImage) {
//		  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//		  inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//		  String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//		  return Uri.parse(path);
//	}
	
	private void proceedOcr() {    
		ImageView mImageView = (ImageView)this.findViewById(R.id.selected_image);    
	    Bitmap imageBitmap;
	    
	    if ((mImageView.getDrawable())==null){
	    	Log.v("MainScreenActivity","Set Defult Image ");
	    	imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_about);
//	    	mImageView.setImageResource(R.drawable.testimagecolor);
//	    	imageBitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
//	    	imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
	    }
	    else {
	    	imageBitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
	    }
	    
		Intent start_intent = new Intent(MainScreenActivity.this, OCRScreenActivity.class);
		start_intent.putExtra("inputValKey", imageBitmap);
		startActivity(start_intent);
		
	}

	private void openGallery() {
		Intent i = new Intent(
				Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	private void takePicture() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	
	private void saveImageBitmap(Bitmap image){
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File myDir = new File(_root);
	    myDir.mkdirs();
		String fname = "IMG"+ timeStamp +".jpg";
		File file = new File (myDir, fname);
		if (file.exists ()) file.delete (); 
		try {
		       FileOutputStream out = new FileOutputStream(file);
		       image.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.flush();
		       out.close();
		
		} catch (Exception e) {
		       e.printStackTrace();
		}
		Log.v("TEST", _root);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK && data != null){
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = extras.getParcelable("data");
		    switch (requestCode){
		    	case REQUEST_IMAGE_CAPTURE: 
			        mImageView.setImageBitmap(imageBitmap);
		    		saveImageBitmap(imageBitmap);
			        OCR._taken = true;
		    	break;
		
		    	case RESULT_LOAD_IMAGE:
//		    		Uri image = data.getData();
//		    		String[] filePathColumn = { MediaStore.Images.Media.DATA };
//		    		 
//				Cursor cursor = getContentResolver().query(image,
//		                    filePathColumn, null, null, null);
//		            cursor.moveToFirst();
//		    
//		            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//		            String picturePath = cursor.getString(columnIndex);
//		            cursor.close();
		            mImageView.setImageBitmap((Bitmap) extras.getParcelable("data"));
//		    		mImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));		
		    }
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		android.os.Debug.stopMethodTracing();
	}
	
}
