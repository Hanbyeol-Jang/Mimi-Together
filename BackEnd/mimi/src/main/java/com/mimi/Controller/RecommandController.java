package com.mimi.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.Dto.Party;
import com.mimi.Dto.Recommand;
import com.mimi.Dto.RecommandRequest;
import com.mimi.Dto.RecommandRequest.Survey;
import com.mimi.Dto.User;
import com.mimi.Service.PartyService;
import com.mimi.Service.RecommandService;
import com.mimi.Service.UserService;

@CrossOrigin(origins = { "*" })

@RestController
@RequestMapping("/recom")
public class RecommandController {

	@Autowired
	private RecommandService recommandService;

	@Autowired
	private PartyService partyService;
	@Autowired
	private UserService userService;

	@PostMapping("/survey")
	public ResponseEntity<?> create(@RequestBody final Survey[] request)
			throws IOException, InterruptedException {
		System.out.println(request);
		recommandService.survey(request);
		// recommand_update();
		System.out.println("review update success!!");
		String id = request[0].getUid();
		userService.modifySurvey(id);
		System.out.println("survey success!!!");
		
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@GetMapping("/multi")
	public ResponseEntity<?> recommand_save(@RequestParam final String PartyId)
			throws InterruptedException, IOException {
		Party party = partyService.getParty(PartyId);
		List<User> user_list = party.getUserList();
		List<List<Recommand>> ls = new LinkedList<List<Recommand>>();
		List<Recommand> ret = new LinkedList<Recommand>();
		System.out.println("multi recommand....");
		System.out.println(user_list);
		for (int i = 0; i < user_list.size(); i++) {
			List<Recommand> t = recommandService.recom(user_list.get(i).getId(), party.getPromiseLocation());
			System.out.println(t);
			ls.add(t);
		}
		System.out.println(ls);
		for (int i = 0; i < ls.get(0).size() - 1; i++) {
			boolean plag = true;
			double avr_rating = 0;

			for (int j = 0; j < user_list.size(); j++) {
				avr_rating += ls.get(j).get(i).getRating();
				if (ls.get(j).get(i).getRating() < 3) {
					plag = false;
					break;
				}
			}

			if (plag) {
				ls.get(0).get(i).setRating(avr_rating / user_list.size());
				ret.add(ls.get(0).get(i));
			}
			ret.sort(null);
		}

		return new ResponseEntity<List<Recommand>>(ret, HttpStatus.OK);
	}
		
	public void recommand_update() throws IOException, InterruptedException {
		System.out.println("recommand start.....");
		String path = "C:/test/MongoConnect.py";
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
	}

	@GetMapping("/recommand")
	public ResponseEntity<?> recommand() throws InterruptedException, IOException {
		recommand_update();
		return null;
	}
}
