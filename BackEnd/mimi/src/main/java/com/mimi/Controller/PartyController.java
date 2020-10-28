package com.mimi.Controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Party;
import com.mimi.Service.PartyService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/party")
public class PartyController {

	@Autowired
	private PartyService partyService;

	@PostMapping("/create")
	@ApiOperation(value = "모임 생성")
	public ResponseEntity<HashMap<String, Object>> createParty(@RequestBody Party party) {
		System.out.println("create Controller");
		System.out.println(party.getPName());

		try {
			HashMap<String, Object> map = new HashMap<>();
			Party partyCreated = partyService.createParty(party);

			if (partyCreated == null) {
				map.put("party", "fail");
			} else {
				map.put("party", partyCreated);
			}

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "id로 모임 가져오기")
	public ResponseEntity<HashMap<String, Object>> getParty(@PathVariable("id") String id) {
		System.out.println("getParty Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();

			Optional<Party> partyInfo = partyService.getParty(id);
			map.put("Party", partyInfo.get());

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/delete")
	@ApiOperation(value = "모임 삭제")
	public ResponseEntity<HashMap<String, Object>> deleteParty(@RequestBody Party party) {
		System.out.println("deleteParty Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			partyService.deleteParty(party);
			map.put("Party", "deleted");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
