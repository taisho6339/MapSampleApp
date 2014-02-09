package com.taishonet.mapsample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.taishonet.mapsample.config.Config;
import com.taishonet.mapsample.utilities.RequestCallBack;
import com.taishonet.mapsample.utilities.RequestTask;
import com.taishonet.mapsample.utilities.ResponceParser;

public class OverlayRouteMapFragment extends MapFragment implements
		RequestCallBack {

	private GoogleMap mMap;

	@Override
	public void doCallBack(JSONObject json) {
		List<LatLng> list = ResponceParser.getSteps(json);
		drawRoute(list);
	}

	private void drawRoute(List<LatLng> list) {

		PolylineOptions options = new PolylineOptions().width(5)
				.color(Color.BLUE).geodesic(true);
		for (int z = 0; z < list.size(); z++) {
			Log.d(Config.DEBUG_TAG, list.get(z).toString());
			LatLng point = list.get(z);
			options.add(point);
		}
		mMap.addPolyline(options);
	}

	@Override
	public void onResume() {
		super.onResume();
		mMap = getMap();
		mMap.setMyLocationEnabled(true);
		requestSearchRoute();
	}

	public void requestSearchRoute() {
		Map<String, String> values = new HashMap<String, String>();
		values.put("origin", "東京都墨田区押上１−１−２");
		values.put("destination", "東京都渋谷区宇田川町２５−９");
		values.put("sensor", "false");
		values.put("mode", "walking");
		RequestTask task = new RequestTask(Config.DIRECTION_API_URL, values,
				this);
		task.execute();
	}

}
