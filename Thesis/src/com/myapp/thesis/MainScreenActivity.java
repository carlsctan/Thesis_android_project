package com.myapp.thesis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainScreenActivity extends ActionBarActivity {
	static final int REQUEST_IMAGE_CAPTURE = 1;
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
		
	}

	private void openGallery() {
		// TODO Auto-generated method stub
		
	}

	private void takePicture() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        ImageView mImageView = (ImageView)this.findViewById(R.id.selected_image);
	        mImageView.setImageBitmap(imageBitmap);
	    }
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		android.os.Debug.stopMethodTracing();
	}
	
}
