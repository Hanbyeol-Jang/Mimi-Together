package com.mimi.mimi.Temp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.mimi.Temp.Dao.TempDao;
import com.mimi.mimi.Temp.Dto.Temp;

@Service
public class TempService {
	@Autowired
	private TempDao tempdao;
	
	public List<Temp> test() {
		return tempdao.findAll();
	}
}
