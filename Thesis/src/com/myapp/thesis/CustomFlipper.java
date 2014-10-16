package com.myapp.thesis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class CustomFlipper extends ViewFlipper{
	Paint paint = new Paint();

	public CustomFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		int width = getWidth();
		float margin = 2, radius = 5;
		float cx = width/2 - ((radius + margin) * 2 * getChildCount()/2);
		float cy = getHeight() - 15;
		canvas.save();
		for(int i = 0; i < getChildCount(); i++)
		{
			if(i == getDisplayedChild())
			{
				paint.setColor(Color.parseColor("#159b9b"));
				canvas.drawCircle(cx, cy, radius, paint);
			}
			else
			{
				paint.setColor(Color.GRAY);
				canvas.drawCircle(cx, cy, radius, paint);
			}
			cx += 2*(radius+margin);
		}
		canvas.restore();
	}
}
