package com.mimi.Controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Party;
import com.mimi.Dto.Store;
import com.mimi.Service.StoreService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	StoreService storeService;

	@GetMapping()
	public ResponseEntity<?> test() {
		List<Store> list = storeService.findAll();
		System.out.println("test");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
	}
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "id로 가게 가져오기")
	public ResponseEntity<HashMap<String, Object>> getParty(@PathVariable("id") String id) {
		System.out.println("getStore Controller");
		try {
			HashMap<String, Object> map = new HashMap<>();

			Store store = storeService.getStore(id);
			map.put("Store", store);

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	

	@GetMapping("surbey")
	public ResponseEntity<?> surbey() {
		List<Store> list = storeService.findAll();

		List<Store> ret = new LinkedList<Store>();
		for (int i = 0; i < 5; i++) {
			int ran = (int) (Math.random() * 9999999 % list.size());
			ret.add(list.get(ran));
		}
		return new ResponseEntity<List<Store>>(ret, HttpStatus.OK);

	}

}
