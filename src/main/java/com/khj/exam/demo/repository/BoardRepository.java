package com.khj.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.khj.exam.demo.vo.Board;

@Mapper
public interface BoardRepository {
	@Select("""
			select *
			from board as B
			where B.id=#{id}
			And B.delStatus=0
			""")
	public Board getBoardById(int id);	

}
