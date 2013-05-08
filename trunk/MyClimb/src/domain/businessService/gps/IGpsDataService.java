package domain.businessService.gps;

import domain.businessEntity.gps.ClimbData;

public interface IGpsDataService {
	
	//获取登山记录
	public ClimbData getClimbData();
}
