package com.taishonet.mapsample;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

	private OverlayRouteMapFragment mMapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		mMapFragment = new OverlayRouteMapFragment();
		transaction.add(R.id.content, mMapFragment);
		transaction.commit();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
