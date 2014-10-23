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
import android.support.v4.app.FragmentTransaction;
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
	public static final int REQUEST_IMAGE_CAPTURE = 1;
	public static final int REQUEST_LOAD_IMAGE = 3;
	
	public static String _root;
	public static File rootFile;
	private ImageView mImageView;
	OCRScreenActivity OCR= new OCRScreenActivity();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);
		this.getSupportActionBar().show();
		
		//path of app-private directory
		_root = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
		
		//creation of folders to the path _root
		rootFile = new File(_root);
		rootFile.mkdirs();
		
		
		mImageView = (ImageView)this.findViewById(R.id.selected_image);
		ImageView take_photo = (ImageView) this.findViewById(R.id.take_photo);
		take_photo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.childFragment, new BlankFragment(), "blankFragment");
				ft.replace(R.id.MainFragment, new BlankFragment(), "blankFragment");
				ft.commit();
				takePicture();
			}
		});
		ImageView gallery = (ImageView) this.findViewById(R.id.gallery);
		gallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.childFragment, new BlankFragment(), "blankFragment");
				ft.replace(R.id.MainFragment, new BlankFragment(), "blankFragment");
				ft.commit();
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
	

	private void proceedOcr() {
		  ImageView mImageView = (ImageView)this.findViewById(R.id.selected_image);    
		    Bitmap imageBitmap;
		    if ((mImageView.getDrawable())!=null){
			    if ((mImageView.getDrawable())==null){
			    	Log.v("MainScreenActivity","Set Defult Image ");
//			    	imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testimage);
	//		    	mImageView.setImageResource(R.drawable.testimagecolor);
	//		    	imageBitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
	//		    	imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
			    	imageBitmap = null;
			    }
			    else {
			    	imageBitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
			    }
			    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_top_to_bottom, R.anim.slide_top_to_bottom);
				OcrResultFragment newFragment = new OcrResultFragment();
				ft.replace(R.id.MainFragment, newFragment, "detailFragment");
				// Start the animated transition.
				ft.replace(R.id.childFragment, new BlankFragment(), "blankFragment");
				ft.commit();
//				Intent start_intent = new Intent(MainScreenActivity.this, OCRScreenActivity.class);
//				start_intent.putExtra("inputValKey", imageBitmap);
//				startActivity(start_intent);
		    }else{
		    	Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
		    }
		
	}

	private void openGallery() {
		if(rootFile.listFiles().length != 0){
		Intent intent = new Intent(MainScreenActivity.this, GalleryScreenActivity.class);
		startActivityForResult(intent, REQUEST_LOAD_IMAGE);
		}
		else{
			Toast.makeText(this, "No Image in Directory", Toast.LENGTH_SHORT).show();
		}
	}

	private void takePicture() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	private void saveImageBitmap(Bitmap image){
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fname = "IMG"+ timeStamp +".jpg";
		File file = new File (rootFile, fname);
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
		Log.v("resultCode", new Float(resultCode).toString());
		if(resultCode == RESULT_OK && data != null){
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = extras.getParcelable("data");
			Log.v("requestCode", new Float(requestCode).toString());
		    switch (requestCode){
		    	case REQUEST_IMAGE_CAPTURE: 
		    		mImageView.setImageBitmap(imageBitmap);
		    		saveImageBitmap(imageBitmap);
		    		File f = new File(_root);
		    		Log.v("file", Uri.fromFile(f.listFiles()[0]).toString());
		    		
			        OCR._taken = true;
			        break;
		
		    	case REQUEST_LOAD_IMAGE:
		    		Log.v("MAINSCREEN DATA", (Uri.parse(extras.getString("Path")).toString()));
		    		mImageView.setImageURI(Uri.parse(extras.getString("Path")));
		    		break;
		    }
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		android.os.Debug.stopMethodTracing();
	}
}
