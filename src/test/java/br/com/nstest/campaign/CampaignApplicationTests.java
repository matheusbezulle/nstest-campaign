package br.com.nstest.campaign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
		Campaign campaign = getDefaultCampaign();
		campaign.setId(null);
		
		when(campaignRepository.findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(null);
		when(heartTeamRepository.save(any(HeartTeam.class))).thenReturn(new HeartTeam(1));
		when(campaignRepository.save(any(Campaign.class))).thenReturn(getDefaultCampaign());
		
		assertEquals(campaignController.create(campaign).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createNullCampaign() {
		assertEquals(campaignController.create(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createWithId() {
		assertEquals(campaignController.create(getDefaultCampaign()).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void createWithExistentsCampaignWithDateConflict() {
		Campaign campaign = getDefaultCampaign();
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		
		Campaign campaign2 = getDefaultCampaign();
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 2));
		
		Campaign campaign3 = getDefaultCampaign();
		campaign3.setId(null);
		campaign3.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign3.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		
		List<Campaign> campaignList = new ArrayList<>();
		campaignList.add(campaign);
		campaignList.add(campaign2);
		
		when(campaignRepository.findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(campaignList);
		when(campaignRepository.saveAll(anyList())).thenReturn(Arrays.asList(campaign, campaign2, campaign3));
		
		assertEquals(campaignController.create(campaign3).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createWithExistentsCampaignWithoutDateConflict() {
		Campaign campaign = getDefaultCampaign();
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		
		Campaign campaign2 = getDefaultCampaign();
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 2));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 5));
		
		Campaign campaign3 = getDefaultCampaign();
		campaign3.setId(null);
		campaign3.setValidityInitDate(LocalDate.of(2020, 3, 4));
		campaign3.setValidityFinalDate(LocalDate.of(2020, 3, 9));
		
		List<Campaign> campaigns = new ArrayList<>();
		campaigns.add(campaign);
		campaigns.add(campaign2);
		
		when(campaignRepository.findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(campaigns);
		when(campaignRepository.saveAll(anyList())).thenReturn(Arrays.asList(campaign, campaign2, campaign3));
		
		assertEquals(campaignController.create(campaign3).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	void createErrorThrowException() {
		Campaign campaign = getDefaultCampaign();
		campaign.setId(null);
		
		doThrow(new IllegalArgumentException()).when(campaignRepository).findByValidityInitDateBetweenOrValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class));
		
		assertEquals(campaignController.create(campaign).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void readNullObject() {
		assertEquals(campaignController.read(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void readInexistentCampaign() {
		when(campaignRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertEquals(campaignController.read(1).getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	void readExistentNotValidCampaign() {
		Campaign campaign = getDefaultCampaign();
		campaign.setValidityInitDate(LocalDate.of(2019, 3, 1));
		campaign.setValidityFinalDate(LocalDate.of(2019, 3, 3));
		
		when(campaignRepository.findById(anyInt())).thenReturn(Optional.of(campaign));
		assertEquals(campaignController.read(1).getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	void readExistentValidCampaign() {
		when(campaignRepository.findById(anyInt())).thenReturn(Optional.of(getDefaultCampaign()));
		assertEquals(campaignController.read(1).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void readErrorThrowException() {
		doThrow(new IllegalArgumentException()).when(campaignRepository).findById(anyInt());
		assertEquals(campaignController.read(1).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void findByHeartTeamNullId() {
		assertEquals(campaignController.findByHeartTeam(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void findByHeartTeam() {
		when(campaignRepository.findByHeartTeamId(anyInt())).thenReturn(new ArrayList<>());
		assertEquals(campaignController.findByHeartTeam(1).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void findByHeartTeamErrorThrowException() {
		doThrow(new IllegalArgumentException()).when(campaignRepository).findByHeartTeamId(anyInt());
		assertEquals(campaignController.findByHeartTeam(1).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void updateNullCampaign() {
		assertEquals(campaignController.update(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void updateCampaignWithNullId() {
		assertEquals(campaignController.update(new Campaign()).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void updateCampaign() {
		assertEquals(campaignController.update(getDefaultCampaign()).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void updateErrorThrowException() {
		doThrow(new IllegalArgumentException()).when(campaignRepository).save(any(Campaign.class));
		assertEquals(campaignController.update(getDefaultCampaign()).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	void deleteCampaignWithNullId() {
		assertEquals(campaignController.delete(null).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void deleteCampaign() {
		assertEquals(campaignController.delete(1).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void deleteErrorThrowException() {
		doThrow(new IllegalArgumentException()).when(campaignRepository).deleteById(anyInt());
		assertEquals(campaignController.delete(1).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private static Campaign getDefaultCampaign() {
		Campaign campaign = new Campaign();
		campaign.setId(1);
		campaign.setName("Campaign 01");
		campaign.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign.setValidityFinalDate(LocalDate.now().plusDays(1));
		campaign.setHeartTeam(new HeartTeam(1));
		return campaign;
	}
	
}
