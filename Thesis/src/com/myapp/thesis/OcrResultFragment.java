package com.myapp.thesis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

public class OcrResultFragment extends Fragment{
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View v = inflater.inflate(R.layout.ocr_result_fragment, container, false);
		 Button iv = (Button)v.findViewById(R.id.calBtn);
		 iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				solveSystem();
			}
		});
		 return v;
	}

	public void solveSystem(){
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.slide_top_to_bottom, R.anim.slide_top_to_bottom);
		ChildFragment newFragment = new ChildFragment();
		ft.replace(R.id.childFragment, newFragment, "detailFragment");
		// Start the animated transition.
		ft.commit();

		
	}
}
