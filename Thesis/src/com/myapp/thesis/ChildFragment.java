package com.myapp.thesis;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChildFragment extends ListFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		String value []={"X = 1","Y = 1","Z = 0","System is Consistent"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.calculate_result_fragment,R.id.list,value);
		setListAdapter(adapter);
//		View v = inflater.inflate(R.layout.calculate_result_fragment,container,false);
//		return v;
		return super.onCreateView(inflater, container, savedInstanceState);	
	}
	


}
