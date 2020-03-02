package br.com.nstest.campaign.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.nstest.campaign.entity.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
	
	@Query(value = "SELECT c FROM Campaign c WHERE (c.validityInitDate BETWEEN :vid AND :vfd) OR (c.validityFinalDate BETWEEN :vid AND :vfd)")
	List<Campaign> findByValidityInitDateBetweenOrValidityFinalDateBetween(@Param(value = "vid") LocalDate validityInitDate, @Param(value = "vfd") LocalDate validityFinalDate);
	
	List<Campaign> findByHeartTeamId(Integer id);
	
}