package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository	//@Autowired 할수 있는 얘
public class PhoneDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//전체 리스트 가져오기
	public List<PersonVo> getPersonList() {
		//일반적으로 그냥 select 할때는 이름만 넣어줌.
		//sqlSession.selectList("이름");
		//검색 기능 같은 경우는 제공할 데이터를 뒤에 적어줌.
		//sqlSession.selectList("이름", 데이터);
		
		System.out.println("dao: getPersonList()");
		//List<PersonVo> personList = sqlSession.selectList("전체가져오기 sql");
		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList2");
		
		System.out.println(personList.toString());
		return personList;
	}
	
	//전화번호 저장(insert)
	public void personInsert(PersonVo personVo) {
		System.out.println(personVo.toString());
		//sqlSession.insert("이름", 데이터);
		sqlSession.insert("phonebook.insert", personVo);
	}
	
	//전화번호 삭제
	public void personDelete(int personId) {
		System.out.println("dao:personDelete() => " + personId);
		
		//sqlSession.delete("이름", 데이터)
		int count = sqlSession.delete("phonebook.delete", personId);
		System.out.println(count);
	}
	
	//사람 1명 데이터 가져오기
	public PersonVo getPerson(int personId) {
		//검색 기능 같은 경우는 제공할 데이터를 뒤에 적어줌.
		//sqlSession.selectOne("이름", 데이터);
		//sqlSession.selectOne("1명가져오기 sql", personId);
		System.out.println("dao:getPerson() => " + personId);
		
		PersonVo personVo = sqlSession.selectOne("phonebook.selectOne", personId);
		System.out.println(personVo.toString());
		
		return personVo;
	}
	
	public Map<String, Object> getPerson2(int personId) {
		System.out.println("dao:getPerson2()" + personId);
		
		Map<String, Object> personMap = sqlSession.selectOne("phonebook.selectOne2", personId);
		System.out.println(personMap.toString());
		
		String name = (String)personMap.get("NAME");
		System.out.println(name);
		
		int id = Integer.parseInt(String.valueOf(personMap.get("PERSONID")));
		System.out.println(id);

		return personMap;
	}
	
	//전화번호 수정(업데이트)
	public void personUpdate(PersonVo personVo) {
		System.out.println("dao:personUpdate()");
		System.out.println(personVo.toString());
		
		//sqlSession.update("이름", 데이터);
		sqlSession.update("phonebook.update", personVo);
	}	
	
	//수정2
	public void personUpdate2(int personId, String name, String hp, String company) {
		System.out.println("dao:personUpdate2() => " + personId + "," + name + "," + hp + "," + company);
		
		//이건 에러. 잘못된거임. update할때 값은 한개로만 넘겨줄 수 잇음.
		//sqlSession.update("phonebook.update2", personId, name, hp, company);
		
		//이 방식을 이용해도 됨
		/*
		PersonVo personVo = new PersonVo(personId, name, hp, company);
		sqlSession.update("phonebook.update2", personVo);
		*/
		
		//vo 대신 --> map을 이용하는 방법
		Map<String, Object> personMap = new HashMap<String, Object>();
		personMap.put("id", personId);
		personMap.put("name", name);
		personMap.put("hp", hp);
		personMap.put("company", company);
		
		System.out.println(personMap.toString());
		
		sqlSession.update("phonebook.update2", personMap);
		
		
	}
	
}