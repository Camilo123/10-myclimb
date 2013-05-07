package domain.businessEntity.systemManagement;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "T_User")

public class User {
	@DatabaseField(generatedId = true)
	private int userID; //用户ID,对象ID
	@DatabaseField(canBeNull = false)
	private String userName;//用户名
	@DatabaseField(canBeNull = false)
	private String passWord;//密码
	@DatabaseField(canBeNull = false)
	private float weight;//体重
	
	@DatabaseField(canBeNull = false)
	private float height;//身高
	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	//构造函数
	public User() {
		// ORMLite needs a no-arg constructor
	}
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String password) {
		this.passWord = password;
	}
	
}
