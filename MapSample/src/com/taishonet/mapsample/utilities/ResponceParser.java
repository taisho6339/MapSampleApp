package com.taishonet.mapsample.utilities;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class ResponceParser {

	public static List<LatLng> getSteps(JSONObject resData) {
		List<LatLng> list = null;

		try {

			JSONArray routeArray = resData.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes
                    .getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            list = MapUtil.decodePoly(encodedString);
            
		} catch (JSONException e) {
		}

		return list;
	}
}
