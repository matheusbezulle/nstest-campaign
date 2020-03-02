package br.com.nstest.campaign.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"campaigns", "users"})
public class HeartTeam implements Serializable {
	
	private static final long serialVersionUID = -4269582499787991546L;

	@Id
	@Column(name = "ID_HEART_TEAM")
	private Integer id;
	
	@OneToMany(mappedBy = "heartTeam")
	private List<Campaign> campaigns;
	
	@OneToMany(mappedBy = "heartTeam")
	private List<User> users;
	
	public HeartTeam() { }
	
	public HeartTeam(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
