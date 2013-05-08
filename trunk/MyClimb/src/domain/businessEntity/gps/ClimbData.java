package domain.businessEntity.gps;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "T_ClimbData")
public class ClimbData {
	//ID号 设置主键
	@DatabaseField(generatedId = true)
	private int climbID;
	
	//设置登山记录名称
	@DatabaseField(canBeNull = false)
	private String climbName;
	
	//平均速度
	@DatabaseField(canBeNull = true)
	private String avgSpeed;
	
	//开始时间
	@DatabaseField(canBeNull = true)
	private Date startTime;
	
	//结束时间
	@DatabaseField(canBeNull = true)
	private Date stopTime;
	
	//经度
	@DatabaseField(canBeNull = true)
	private String longitude;
	
	//纬度
	@DatabaseField(canBeNull = true)
	private String latitude;
	
	public int getClimbID() {
		return climbID;
	}
	public void setClimbID(int climbID) {
		this.climbID = climbID;
	}
	public String getClimbName() {
		return climbName;
	}
	public void setClimbName(String climbName) {
		this.climbName = climbName;
	}
	public String getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(String avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getStopTime() {
		return stopTime;
	}
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
