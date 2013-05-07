package domain.businessService.systemManagement;

import domain.businessEntity.systemManagement.User;

public interface ISystemManagementService {

	//登录函数的声明，参数为用户名
	public User login(String userName);
	
	//（user）参数为User对象
//	public boolean register(User user);
	
	//注册函数的声明，参数为用户名和密码
	public boolean register(String userName,String passWord);
}
