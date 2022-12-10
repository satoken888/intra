package jp.co.kawakyo.intra.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account {
	protected Account() {}
	
	public Account(String username,String password,boolean isAdmin){
		setId(0L);
		setUsername(username);
		setPassword(password);
		setEnabled(true);
		setAdmin(isAdmin);
	}
	
	public Account(Long id, String username, String password,boolean isEnabled,boolean isAdmin) {
		setId(id);
		setUsername(username);
		setPassword(password);
		setEnabled(isEnabled);
		setAdmin(isAdmin);
	}

	public Account(Long id,String username,String password,boolean isEnabled,boolean isAdmin,Date delete_date,Date update_date){
		setId(id);
		setUsername(username);
		setPassword(password);
		setEnabled(isEnabled);
		setAdmin(isAdmin);
		setDeleteDate(delete_date);
		setUpdateDate(update_date);
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
    private boolean enabled;
	
	@Column(nullable = false)
    private boolean admin;

	private Date delete_date;

	private Date update_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}

	public Date getDeleteDate(){
		return delete_date;
	}

	public void setDeleteDate(Date delete_date){
		this.delete_date = delete_date;
	}

	public Date getUpdateDate(){
		return update_date;
	}

	public void setUpdateDate(Date update_date){
		this.update_date = update_date;
	}
}