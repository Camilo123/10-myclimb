package ui.viewModel.gps;

import java.util.Date;
import java.util.List;

import ui.viewModel.ModelErrorInfo;
import ui.viewModel.ViewModel;

public class RecDetailViewModel extends ViewModel {
	private String ClimbName;
	private Date currentDate;
	private String altitudeDiff;
	private String avgSpeed;
	private Date time;
	private String longitude;
	private String latitude;
	
	public String getClimbName() {
		return ClimbName;
	}
	public void setClimbName(String climbName) {
		ClimbName = climbName;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
