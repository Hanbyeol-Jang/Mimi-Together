package com.mimi.mimi.Temp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.mimi.Temp.Dto.Temp;
import com.mimi.mimi.Temp.Service.TempService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/temp")
public class TempController {

	@Autowired
	TempService tempService;
	
	
	@GetMapping()
	public ResponseEntity<?> test(){
		List<Temp> list = tempService.test();
		
//		tempService.save(new Temp("test", "삼성", "후자"));
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return new ResponseEntity<Object>(tempService.test(), HttpStatus.OK);
	}
}
