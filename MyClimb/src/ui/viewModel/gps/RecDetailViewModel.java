package ui.viewModel.gps;

import java.util.Date;
import java.util.List;

import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;

public class RecDetailViewModel extends ViewModel {
	private String ClimbName;
	private String altitudeDiff;
	private String avgSpeed;
	private Date startTime;
	private Date stopTime;
	private String longitude;
	private String latitude;
	
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
	public String getClimbName() {
		return ClimbName;
	}
	public void setClimbName(String climbName) {
		ClimbName = climbName;
	}
	public String getAltitudeDiff() {
		return altitudeDiff;
	}
	public void setAltitudeDiff(String altitudeDiff) {
		this.altitudeDiff = altitudeDiff;
	}
	public String getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(String avgSpeed) {
		this.avgSpeed = avgSpeed;
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
	@Override
	public List<ModelErrorInfo> verifyModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
