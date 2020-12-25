package com.tchappy.photo.util;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class GetLocation implements BDLocationListener{

	@Override
	public void onReceiveLocation(BDLocation location) {
		//Receive Location 
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		System.out.println("getLocType:"+location.getLocType());
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\ndirection : ");
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append(location.getDirection());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			System.out.println("getProvince:"+location.getProvince());
			System.out.println("getCity:"+location.getCity());
			System.out.println("getDistrict:"+location.getDistrict());
			System.out.println("getStreet:"+location.getStreet());
			//运营商信息
			sb.append("\noperationers : ");
			sb.append(location.getOperators());
		}
	}
}
