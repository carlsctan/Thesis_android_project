package com.myapp.thesis;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class StartScreenActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getSupportActionBar().hide();
		setContentView(R.layout.initial_screen);
		
		RelativeLayout rlayout = (RelativeLayout)this.findViewById(R.id.container);
		rlayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickStartBtn();				
			}
		});
		
		TextView start_text = (TextView) this.findViewById(R.id.start_view);
		
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(300); //You can manage the blinking time with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		start_text.startAnimation(anim);
		
//		start_btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				onClickStartBtn();				
//			}
//		});
		
		
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	public void onClickStartBtn(){
		Intent start_intent = new Intent(this, MainScreenActivity.class);
		startActivity(start_intent);
	}
	
	public void onClickHelpBtn(){
		Intent help_intent = new Intent(StartScreenActivity.this, MainScreenActivity.class);
		startActivity(help_intent);
	}
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}

}
