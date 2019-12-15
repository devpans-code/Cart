package com.cartapp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cartapp.database.DatabaseService;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String type;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int addUser(User u) throws SQLException {
		String sql = "INSERT INTO user (name, email, password) VALUES ('" + u.getName() + "','" + u.getEmail() + "','" + u.getPassword() + "')";
		try {
			return DatabaseService.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<User> fetchUser() {
		List<User> userList = new ArrayList<User>();
		String sql = "SELECT id, name, email, type, status FROM user";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null)
		{
			User p = null;
			try {
				while (rs.next()) {
					p = new User();
					p.setId(rs.getInt(1));
					p.setName(rs.getString(2));
					p.setEmail(rs.getString(3));
					p.setType(rs.getString(4));
					p.setStatus(rs.getString(5));
					userList.add(p);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return userList;
		}
		return null;
	}
	
	public User fetchUserInfo(User u, String status) throws SQLException {
		String sql = "SELECT id, name, email, type, status FROM user WHERE email ='"+ u.getEmail() +"' AND password ='"+ u.getPassword() +"' AND status = '"+status+"'";
		try {
			ResultSet rs = DatabaseService.select(sql);
			if(rs != null) {
				User user = null;
				if(rs.next()) {
					user = new User();
					user.setId(rs.getInt(1));
					user.setName(rs.getString(2));
					user.setEmail(rs.getString(3));
					user.setType(rs.getString(4));
					user.setStatus(rs.getString(5));
				}
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User fetchUserData(User u) throws SQLException {
		String sql = "SELECT id, name, email, type, status FROM user WHERE id ='"+ u.getId() +"'";
		try {
			ResultSet rs = DatabaseService.select(sql);
			if(rs != null) {
				User user = null;
				if(rs.next()) {
					user = new User();
					user.setId(rs.getInt(1));
					user.setName(rs.getString(2));
					user.setEmail(rs.getString(3));
					user.setType(rs.getString(4));
					user.setStatus(rs.getString(5));
				}
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int updateStatus(User p, String status, int updated_by) {
		String sql = "UPDATE user SET status = '"+status+"', updated_by = '"+updated_by+"' WHERE id = '"+p.getId()+"'";
		try {
			return DatabaseService.update(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

}
