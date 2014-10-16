package com.myapp.thesis;

import java.io.File;
import java.net.URI;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryFragment extends Fragment{

	public static Fragment newInstance(GalleryScreenActivity context, int pos, 
			float scale)
	{
		Bundle b = new Bundle();
		b.putInt("pos", pos);
		b.putFloat("scale", scale);
		return Fragment.instantiate(context, GalleryFragment.class.getName(), b);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		LinearLayout l = (LinearLayout) 
				inflater.inflate(R.layout.gallery_page2, container, false);
		
		int pos = this.getArguments().getInt("pos");
		Log.v("TEXTVIEW", "befor TV");
		TextView tv = (TextView) l.findViewById(R.id.text);
	    Log.v("imageView", "before imageview");
		ImageView image = (ImageView)l.findViewById(R.id.gallery_image);
	    
//		ImageItems items = new ImageItems();
		File []f = MainScreenActivity.rootFile.listFiles(); 
		if(f.length !=0){
			Log.v("FILE", Uri.fromFile(f[pos]).toString());
			final Uri fileUri = Uri.fromFile(f[pos]);
			image.setImageURI(fileUri);
			tv.setText(MainScreenActivity.rootFile.list()[pos]);
			image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.v("ACTIVITY",getActivity().toString());
					((GalleryScreenActivity)getActivity()).getImage(fileUri);
					
				}
				
			});
		}
		GalleryLinearLayout root = (GalleryLinearLayout) l.findViewById(R.id.root);
		float scale = this.getArguments().getFloat("scale");
		root.setScaleBoth(scale);
		
		return l;
	}
	
	
	public int getFragmentPosition(){
		return this.getArguments().getInt("pos");
	}
	
}
