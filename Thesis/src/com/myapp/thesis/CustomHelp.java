package com.myapp.thesis;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class CustomHelp extends Activity implements OnTouchListener{
	private float downXValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setContentView(R.layout.help_page);
		
		RelativeLayout main = (RelativeLayout) findViewById(R.id.container);
		main.setOnTouchListener((OnTouchListener) this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ViewFlipper vf = (ViewFlipper) findViewById(R.id.flip);
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				downXValue = event.getX();
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				float currentX = event.getX();
				v.performClick();
				if(downXValue < currentX)
				{
					if(vf.getDisplayedChild() == 0) break;
					//vf.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade));
					vf.setInAnimation(v.getContext(), R.anim.push_right_out);
					vf.setOutAnimation(v.getContext(), R.anim.push_right_in);
					vf.showPrevious();
				}
				if(downXValue > currentX)
				{
					if(vf.getDisplayedChild() == 2) break;
					vf.setInAnimation(v.getContext(), R.anim.push_left_in);
					vf.setOutAnimation(v.getContext(), R.anim.push_left_out);
					vf.showNext();
				}
				break;
			}
			default: break;
		}
		return true;
	}

}
