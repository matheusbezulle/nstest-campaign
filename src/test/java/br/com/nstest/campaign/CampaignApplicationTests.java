package br.com.nstest.campaign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import br.com.nstest.campaign.controller.CampaignController;
import br.com.nstest.campaign.entity.Campaign;
import br.com.nstest.campaign.entity.HeartTeam;
import br.com.nstest.campaign.repository.CampaignRepository;
import br.com.nstest.campaign.repository.HeartTeamRepository;

@SpringBootTest
class CampaignApplicationTests {
	
	@InjectMocks
	private CampaignController campaignController;
	
	@Mock
	private CampaignRepository campaignRepository;
	
	@Mock
	private HeartTeamRepository heartTeamRepository;
	
	@Test
	void createFirstCampaign() {
		Campaign campaign = new Campaign();
		campaign.setName("First Campaign");
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign.setHeartTeam(new HeartTeam(1));
		
		when(campaignRepository.findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(null);
		when(heartTeamRepository.save(any(HeartTeam.class))).thenReturn(new HeartTeam(1));
		when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);
		
		assertEquals(campaignController.create(campaign).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createNullCampaign() {
		assertEquals(campaignController.create(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createWithId() {
		Campaign campaign = new Campaign();
		campaign.setId(1);
		assertEquals(campaignController.create(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createWithExistentsCampaignWithDateConflict() {
		Campaign campaign = new Campaign();
		campaign.setName("Campaign 01");
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign.setHeartTeam(new HeartTeam(1));
		
		Campaign campaign2 = new Campaign();
		campaign2.setName("Campaign 02");
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 2));
		campaign2.setHeartTeam(new HeartTeam(1));
		
		Campaign campaign3 = new Campaign();
		campaign2.setName("Campaign 03");
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign2.setHeartTeam(new HeartTeam(1));
		
		List<Campaign> campaigns = new ArrayList<>();
		campaigns.add(campaign);
		campaigns.add(campaign2);
		
		when(campaignRepository.findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(campaigns);
		campaigns.add(campaign3);
		when(campaignRepository.saveAll(anyList())).thenReturn(campaigns);
		
		assertEquals(campaignController.create(campaign3).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createWithExistentsCampaignWithoutDateConflict() {
		Campaign campaign = new Campaign();
		campaign.setName("Campaign 01");
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign.setHeartTeam(new HeartTeam(1));
		
		Campaign campaign2 = new Campaign();
		campaign2.setName("Campaign 02");
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 2));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 5));
		campaign2.setHeartTeam(new HeartTeam(1));
		
		Campaign campaign3 = new Campaign();
		campaign2.setName("Campaign 03");
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 4));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 9));
		campaign2.setHeartTeam(new HeartTeam(1));
		
		List<Campaign> campaigns = new ArrayList<>();
		campaigns.add(campaign);
		campaigns.add(campaign2);
		
		when(campaignRepository.findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(campaigns);
		campaigns.add(campaign3);
		when(campaignRepository.saveAll(anyList())).thenReturn(campaigns);
		
		assertEquals(campaignController.create(campaign3).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createErrorThrowException() {
		Campaign campaign = new Campaign();
		campaign.setName("Campaign 01");
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign.setHeartTeam(new HeartTeam(1));
		
		doThrow(new Exception()).when(campaignRepository).findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class));
		
		assertEquals(campaignController.create(campaign).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
