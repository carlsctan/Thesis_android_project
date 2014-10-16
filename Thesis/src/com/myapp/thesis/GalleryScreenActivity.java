package com.myapp.thesis;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class GalleryScreenActivity extends FragmentActivity {
	public static int PAGES;
	// You can choose a bigger number for LOOPS, but you know, nobody will fling
	// more than 1000 times just in order to test your "infinite" ViewPager :D 
	public final static int LOOPS = 1000; 
	public static int FIRST_PAGE;
	public final static float BIG_SCALE = 1.2f;
	public final static float SMALL_SCALE = 0.6f;
	public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
	
	public GalleryPagerAdapter adapter;
	public ViewPager pager;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_page);
	
		pager = (ViewPager) findViewById(R.id.myviewpager);
		PAGES = MainScreenActivity.rootFile.listFiles().length;	
		FIRST_PAGE = PAGES * LOOPS / 2;
		adapter = new GalleryPagerAdapter(this, this.getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(adapter);
		
		
		// Set current item to the middle page so we can fling to both
		// directions left and right
		pager.setCurrentItem(FIRST_PAGE);
		
		// Necessary or the pager will only have one extra page to show
		// make this at least however many pages you can see
		pager.setOffscreenPageLimit(3);
		
		// Set margin for pages as a negative number, so a part of next and 
		// previous pages will be showed
		pager.setPageMargin(-200);
	}
	
	public void getImage(Uri imageUri){
		
		Intent data = new Intent();
		Log.v("getImage URI STRING",imageUri.toString());
		data.putExtra("Path", imageUri.toString());
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, data);
		} else {
		    getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
		
	}
}
