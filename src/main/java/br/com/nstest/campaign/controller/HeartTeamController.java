package br.com.nstest.campaign.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nstest.campaign.entity.HeartTeam;
import br.com.nstest.campaign.repository.HeartTeamRepository;

@RestController
@RequestMapping("heartTeam")
public class HeartTeamController {
	
	@Autowired
	private HeartTeamRepository repository;
	
	@GetMapping
	public ResponseEntity<HeartTeam> findHeartTeam(@RequestParam Integer id) {
		if(Objects.nonNull(id)) {
			try {
				return new ResponseEntity<HeartTeam>(repository.findById(id).get(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}
