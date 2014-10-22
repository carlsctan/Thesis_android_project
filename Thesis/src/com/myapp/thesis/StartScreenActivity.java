package com.myapp.thesis;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartScreenActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getSupportActionBar().hide();
		setContentView(R.layout.initial_screen);
		
		RelativeLayout rlayout = (RelativeLayout)findViewById(R.id.container);
		rlayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickStartBtn(v);
			}
		});
		
		TextView start_text = (TextView) this.findViewById(R.id.start_view);
		
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(300); //You can manage the blinking time with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		start_text.startAnimation(anim);
	}

	public void onClickStartBtn(View v){
		Intent start_intent = new Intent(this, MainScreenActivity.class);
		startActivity(start_intent);
	}
	
	public void onClickHelpBtn(View v){
		Intent help_intent = new Intent(this, CustomHelp.class);
		startActivity(help_intent);
	}
	public void onClickInfoBtn(View v){
		CustomDialog cd = new CustomDialog(this);
		cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		cd.show();
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
