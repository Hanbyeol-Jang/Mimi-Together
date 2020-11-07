package com.mimi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.RecommandDao;
import com.mimi.Dao.ReviewDao;
import com.mimi.Dto.Recommand;
import com.mimi.Dto.RecommandRequest.Survey;
import com.mimi.Dto.Review;

@Service
public class RecommandService {

	@Autowired
	private ReviewDao reviewDao;

	@Autowired
	private RecommandDao recommandDao;

	public void survey(Survey[] list) {
		int index = (int) reviewDao.count();
		for (int i = 0; i < list.length; i++) {
			Review temp = new Review();
			temp.setId(String.valueOf(index+1+i));
			temp.setRating(list[i].getRating());
			temp.setResId(list[i].getRid());
			temp.setUserName("" + list[i].getUid());
			reviewDao.save(temp);
		}
	}

	public Recommand save(Recommand recommand) {
		recommandDao.save(recommand);
		return recommand;
	}

	public List<Recommand> findAll() {
		return recommandDao.findAll();
	}

	public List<Recommand> recom(String name, String address) {
//		address = "%"+address+"%";
		return recommandDao.findByUidAndAddressLikeOrderByRid(name, address);
	}

}
