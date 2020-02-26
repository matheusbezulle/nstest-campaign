package br.com.nstest.campaign.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nstest.campaign.entity.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
	
	List<Campaign> findByValidityInitDateBetween(LocalDate validityInitDate, LocalDate validityFinalDate);
	
	List<Campaign> findByValidityFinalDateBetween(LocalDate validityInitDate, LocalDate validityFinalDate);
	
}