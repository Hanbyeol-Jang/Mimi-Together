package com.mimi.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Recommand;
import com.mimi.Dto.Store;
import com.mimi.Service.StoreService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	StoreService storeService;
	
	
	@GetMapping()
	public ResponseEntity<?> test(){
		List<Store> list = storeService.findAll();
		System.out.println("test");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/recom")
	public ResponseEntity<?> recommand_save() throws InterruptedException, IOException{
		List<Recommand> ls = storeService.recom(" dany","역삼");
		for(int i = 0;i<10;i++) {
			System.out.println(ls.get(i));
		}
		return null;
	}
	
	
	@GetMapping("/recommand")
	public ResponseEntity<?> recommand() throws InterruptedException, IOException{
		List<Store> list = storeService.findAll();
		System.out.println("recommand start.....");
		
		String path = "/home/ubuntu/dev/s03p31b106/BackEnd/mimi/src/main/resource/bigdata/MongoConnect.py";
//		String path = "C:/test/MongoConnect.py";
		
		ProcessBuilder processBuilder = new ProcessBuilder("python", path);
		processBuilder.redirectErrorStream(true);
		Process p = processBuilder.start();
		p.waitFor();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		while ((line = bfr.readLine()) != null) {
		System.out.println(line);
		}
		
		
		System.out.println("recommand end.....");
		return new ResponseEntity<List<Store>>(list, HttpStatus.OK);
	}
}
