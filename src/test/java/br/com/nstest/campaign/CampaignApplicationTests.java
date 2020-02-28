package br.com.nstest.campaign;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.nstest.campaign.controller.CampaignController;
import br.com.nstest.campaign.entity.Campaign;
import br.com.nstest.campaign.repository.CampaignRepository;

@SpringBootTest
class CampaignApplicationTests {
	
	@InjectMocks
	private CampaignController controller;
	
	@Mock
	private CampaignRepository repository;
	
	@Test
	void contextLoads() {
		
		Campaign campaign1 = new Campaign();
		campaign1.setName("campanha 1");
		campaign1.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign1.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign1.setHeartTeamId(1);
		
		Campaign campaign2 = new Campaign();
		campaign2.setName("campanha 2");
		campaign2.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign2.setValidityFinalDate(LocalDate.of(2020, 3, 2));
		campaign2.setHeartTeamId(1);
		
		List<Campaign> campaignList = new ArrayList<>();
		campaignList.add(campaign1);
		campaignList.add(campaign2);
		
		Campaign campaign3 = new Campaign();
		campaign3.setName("campanha 3");
		campaign3.setValidityInitDate(LocalDate.of(2020, 3, 1));
		campaign3.setValidityFinalDate(LocalDate.of(2020, 3, 3));
		campaign3.setHeartTeamId(1);
		
		when(repository.findByValidityFinalDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(campaignList);
		
		controller.create(campaign3);
	}

}
