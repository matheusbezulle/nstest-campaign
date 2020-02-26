package br.com.nstest.campaign.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Campaign implements Serializable {
	
	private static final long serialVersionUID = 3699003352560701616L;

	@Id
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private Integer heartTeamId;
	
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate validityInitDate;
	
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate validityFinalDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHeartTeamId() {
		return heartTeamId;
	}

	public void setHeartTeamId(Integer heartTeamId) {
		this.heartTeamId = heartTeamId;
	}

	public LocalDate getValidityInitDate() {
		return validityInitDate;
	}

	public void setValidityInitDate(LocalDate validityInitDate) {
		this.validityInitDate = validityInitDate;
	}

	public LocalDate getValidityFinalDate() {
		return validityFinalDate;
	}

	public void setValidityFinalDate(LocalDate validityFinalDate) {
		this.validityFinalDate = validityFinalDate;
	}
	
}
