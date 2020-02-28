package br.com.nstest.campaign.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 5676143151416948449L;

	@Id
	private Integer userId;
	
	@Column
	private Integer heartTeamId;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Integer getHeartTeamId() {
		return heartTeamId;
	}

	public void setHeartTeamId(Integer heartTeamId) {
		this.heartTeamId = heartTeamId;
	}
	
}