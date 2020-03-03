package br.com.nstest.campaign.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@SequenceGenerator(name = "SQ_CAMPAIGN", sequenceName = "SEQ_CAMPAIGN", allocationSize = 1)
public class Campaign implements Serializable {
	
	private static final long serialVersionUID = 3699003352560701616L;

	@Id
	@GeneratedValue(generator = "SQ_CAMPAIGN", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ID_HEART_TEAM")
	private HeartTeam heartTeam;

	@Column
	private String name;
	
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

	public HeartTeam getHeartTeam() {
		return heartTeam;
	}

	public void setHeartTeam(HeartTeam heartTeam) {
		this.heartTeam = heartTeam;
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
