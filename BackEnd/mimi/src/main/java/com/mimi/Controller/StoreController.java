package com.mimi.Controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Store;
import com.mimi.Service.StoreService;

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
