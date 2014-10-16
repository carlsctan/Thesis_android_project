package com.myapp.thesis;

import java.io.File;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class GalleryPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener {

	private GalleryLinearLayout cur = null;
	private GalleryLinearLayout next = null;
	private GalleryScreenActivity context;
	private FragmentManager fm;
	private float scale;

	public GalleryPagerAdapter(GalleryScreenActivity context, FragmentManager fm) {
		super(fm);
		this.fm = fm;
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) 
	{
        // make the first pager bigger than others
        if (position == GalleryScreenActivity.FIRST_PAGE)
        	scale = GalleryScreenActivity.BIG_SCALE;     	
        else
        	scale = GalleryScreenActivity.SMALL_SCALE;
        
        position = position % GalleryScreenActivity.PAGES;
        return GalleryFragment.newInstance(context, position, scale);
	}

	@Override
	public int getCount()
	{		
		return GalleryScreenActivity.PAGES * GalleryScreenActivity.LOOPS;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) 
	{	
		if (positionOffset >= 0f && positionOffset <= 1f)
		{
			cur = getRootView(position);
			next = getRootView(position +1);

			cur.setScaleBoth(GalleryScreenActivity.BIG_SCALE 
					- GalleryScreenActivity.DIFF_SCALE * positionOffset);
			next.setScaleBoth(GalleryScreenActivity.SMALL_SCALE 
					+ GalleryScreenActivity.DIFF_SCALE * positionOffset);
		}
	}

	@Override
	public void onPageSelected(int position) {}
	
	@Override
	public void onPageScrollStateChanged(int state) {}
	
	private GalleryLinearLayout getRootView(int position)
	{
		return (GalleryLinearLayout) 
				fm.findFragmentByTag(this.getFragmentTag(position))
				.getView().findViewById(R.id.root);
	}
	
	private String getFragmentTag(int position)
	{
	    return "android:switcher:" + context.pager.getId() + ":" + position;
	}
}
