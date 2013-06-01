package domain.businessEntity.gps;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "T_LatLngData")
public class LatLngData {
	//设置ID为主键
	@DatabaseField(generatedId = true)
	private int dataID;
	//设置开始记录时间作为标识
	@DatabaseField(canBeNull = false)
	private Date time;
	//纬度
	@DatabaseField(canBeNull = true)
	private double lat;
	//经度
	@DatabaseField(canBeNull = true)
	private double lng;
	
	public int getDataID() {
		return dataID;
	}
	public void setDataID(int dataID) {
		this.dataID = dataID;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public Date getStartTime() {
		return time;
	}
	public void setStartTime(Date time) {
		this.time = time;
	}
	
}
