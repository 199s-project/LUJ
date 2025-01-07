package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.MovieDTO;

@Mapper
public interface MovieDao {
    List<MovieDTO> getMovieList();

    @Select("SELECT count(*) FROM movietbl")
    int cntMovie();

    void movieInsert(MovieDTO movie);

    @Select("SELECT * FROM movietbl WHERE m_code=#{m_code}")
    MovieDTO movieSelect(Integer m_code);

    void movieUpdate(MovieDTO movie);

    @Select("SELECT p_sysname FROM movietbl WHERE m_code=#{m_code}")
    String getPoster(Integer m_code);

    @Delete("DELETE FROM movietbl WHERE m_code=#{m_code}")
    void movieDelete(Integer m_code);
}
