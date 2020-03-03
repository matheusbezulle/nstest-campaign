package br.com.nstest.campaign.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nstest.campaign.entity.Campaign;
import br.com.nstest.campaign.repository.CampaignRepository;
import br.com.nstest.campaign.repository.HeartTeamRepository;

@RestController
@RequestMapping("campaign")
public class CampaignController {
	
	@Autowired
	private CampaignRepository repository;
	
	@Autowired
	private HeartTeamRepository heartTeamRepository;
	
	@GetMapping
	public ResponseEntity<Campaign> read(@RequestParam Integer campaignId) {
		if(Objects.nonNull(campaignId)) {
			try {
				Optional<Campaign> campaign = repository.findById(campaignId);
				if(campaign.isPresent() && campaign.get().getValidityFinalDate().isAfter(LocalDate.now()))
					return new ResponseEntity<Campaign>(campaign.get(), HttpStatus.OK);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("findByHeartTeam")
	public ResponseEntity<List<Campaign>> findByHeartTeam(@RequestParam Integer heartTeamId) {
		if(Objects.nonNull(heartTeamId)) {
			try {
				return new ResponseEntity<List<Campaign>>(repository.findByHeartTeamId(heartTeamId), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping
	public ResponseEntity<Campaign> create(@RequestBody Campaign campaign) {
		if(Objects.nonNull(campaign) && Objects.isNull(campaign.getId())) {
			try {
				List<Campaign> campaignList = repository.findByValidityInitDateBetweenOrValidityFinalDateBetween(campaign.getValidityInitDate(), campaign.getValidityFinalDate());
				
				if(!CollectionUtils.isEmpty(campaignList)) {
					campaignList.forEach(c -> c.setValidityFinalDate(c.getValidityFinalDate().plusDays(1)));
					campaignList.add(campaign);
					
					while(campaignList.stream().map(Campaign::getValidityFinalDate).distinct().count() != campaignList.size()) {
						campaignList.forEach(c -> {
							if(campaignList.stream().filter(ca -> c.getValidityFinalDate().isEqual(ca.getValidityFinalDate())).count() > 1 && Objects.nonNull(c.getId())) {
								c.setValidityFinalDate(c.getValidityFinalDate().plusDays(1));
							}
						});
					}
					
					List<Campaign> campaignSaveResponse = repository.saveAll(campaignList);
					return new ResponseEntity<Campaign>(campaignSaveResponse.get(campaignSaveResponse.size() - 1), HttpStatus.CREATED);
				}
				
				heartTeamRepository.save(campaign.getHeartTeam());
				return new ResponseEntity<Campaign>(repository.save(campaign), HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<Campaign> update(@RequestBody Campaign campaign) {
		if(Objects.nonNull(campaign) && Objects.nonNull(campaign.getId())) {
			try {
				return new ResponseEntity<Campaign>(repository.save(campaign), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestParam Integer campaignId) {
		if(Objects.nonNull(campaignId)) {
			try {
				repository.deleteById(campaignId);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
