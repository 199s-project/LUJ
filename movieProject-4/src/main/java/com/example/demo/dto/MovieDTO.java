package com.example.demo.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("MovieDTO")
public class MovieDTO {
    private int m_code;
    private String m_name;//����
    private String m_director;//����
    private String m_nation;//����
    private String m_genre;//�帣
    private String m_actor;//�ֿ����
    private String m_open;//������
    private String m_synopsis;//��ȭ ����
    private String p_oriname;//�������̹��� �̸�
    private String p_sysname;//������ ���� �̸�

}
