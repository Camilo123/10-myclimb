package domain.businessService.systemManagement;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;

import domain.businessEntity.systemManagement.User;
import foundation.data.DataContext;
import foundation.data.DataHelper;
import foundation.data.IDataContext;

public class SystemManagementService implements ISystemManagementService {

	private static String Tag="SystemManagementService";
	private IDataContext ctx=null;

	public SystemManagementService(){
		ctx=new DataContext();
	}
	
	
	//登录函数Login的实现
	@Override
		public User login(String userName) {
		//User的创建
			User user=null;
			//构造块的创建
			QueryBuilder<User, Integer> qb=null;
			try {
				//构造按用户名等于传入的userName查询条件
				qb = ctx.getQueryBuilder(User.class, Integer.class);
				qb.where().eq("userName", userName);
				List<User> qbuserList = ctx.query(User.class, Integer.class,
						qb.prepare());
				if (qbuserList.size() != 0)
					user=qbuserList.get(0);
			} catch (SQLException e) {
				Log.e(Tag,e.toString());
			}
		
			return user;
		}

	
	
	//注册函数register的实现
	//参数为（userName,passWord）
	@Override
	public boolean register(String userName, String passWord) {
		// TODO Auto-generated method stub
		try{
			QueryBuilder<User,Integer> qb=ctx.getQueryBuilder(User.class, int.class);
			qb.where().eq("userName", userName);
			List<User> qbuserList=ctx.query(User.class, int.class, qb.prepare());
			if(qbuserList.size()==0){
				User user=new User();
				user.setUsername(userName);
				user.setPassWord(passWord);
				ctx.add(user, User.class, int.class);
				return true;
			}
		}catch(SQLException e){
			Log.e(Tag, e.toString());
		}
		return false;
	}

	

	
	//参数为（user）
//	@Override
//	public boolean register(User user) {
//		// TODO Auto-generated method stub
//		try{
//			QueryBuilder<User,Integer> qb=ctx.getQueryBuilder(User.class, int.class);
//			qb.where().eq("userName", user.getUsername());
//			List<User> qbuserList=ctx.query(User.class, int.class, qb.prepare());
//			if(qbuserList.size()==0){
//				ctx.add(user,User.class,int.class);
//				return true;
//			}
//		}catch(SQLException e){
//			Log.e(Tag, e.toString());
//		}
//		return false;
//	}
}
