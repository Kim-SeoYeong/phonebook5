package com.javaex.dao;

import java.util.List;

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
	
	//전화번호 수정(업데이트)
	public void personUpdate(PersonVo personVo) {
		System.out.println("dao:personUpdate()");
		System.out.println(personVo.toString());
		
		//sqlSession.update("이름", 데이터);
		sqlSession.update("phonebook.update", personVo);
	}	
	
	
}