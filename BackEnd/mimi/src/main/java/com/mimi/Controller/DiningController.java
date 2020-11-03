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

import com.mimi.Dto.Dining;
import com.mimi.Service.DiningService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dining")
public class DiningController {

	@Autowired
	private DiningService diningService;

	@PostMapping("/upload")
	@ApiOperation(value = "경매 등록")
	public ResponseEntity<HashMap<String, Object>> upload(@RequestBody Dining dining) {
		System.out.println("upload Controller");
		System.out.println(dining.getDnName() + " " + dining.getDnLocation());

		try {
			HashMap<String, Object> map = new HashMap<>();
			Dining diningTender = diningService.upload(dining);

			if (dining == null) {
				map.put("dining", "fail");
			} else {
				map.put("dining", diningTender);
			}

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "id로 경매 정보 가져오기")
	public ResponseEntity<HashMap<String, Object>> getDining(@PathVariable("id") String id) {
		System.out.println("dining Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			Optional<Dining> diningInfo = diningService.getDining(id);
			map.put("Dining", diningInfo.get());

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/remove")
	@ApiOperation(value = "경매 취소")
	public ResponseEntity<HashMap<String, Object>> remove(@RequestBody Dining dining) {
		System.out.println("remove Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			diningService.remove(dining);
			map.put("dining", "removed");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
