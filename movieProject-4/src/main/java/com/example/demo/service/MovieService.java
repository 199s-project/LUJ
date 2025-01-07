package com.example.demo.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.MovieDao;
import com.example.demo.dto.MovieDTO;
import com.example.demo.util.PagingUtil;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovieService {
    private ModelAndView mv;

    @Autowired
    private MovieDao mDao;

    private int listCnt = 5;//������ �� ����� �Խñ� ����

    public ModelAndView getMovieList() {
        log.info("getMovieList()");

        mv = new ModelAndView();

        List<MovieDTO> mList = mDao.getMovieList();
        mv.addObject("mList", mList);

        //���ø� ����
        mv.setViewName("index");

        return mv;
    }

    private String getPaging(Integer pageNum) {
        String pageHtml = null;

        //��ü �۰��� ���ϱ�
        int maxNum = mDao.cntMovie();
        //�� ������ �� ������ ������ ��ȣ�� ����
        int pageCnt = 5;

        PagingUtil paging = new PagingUtil(maxNum,
                pageNum, listCnt, pageCnt);

        pageHtml = paging.makePaging();

        return pageHtml;
    }

    public String insertMovie(List<MultipartFile> files,
                              MovieDTO movie,
                              HttpSession session,
                              RedirectAttributes rttr) {
        log.info("insertMovie()");
        String msg = null;
        String view = null;
        String upFile = files.get(0).getOriginalFilename();

        try {
            if(!upFile.equals("")) {
                fileUpload(files, session, movie);
            }
            mDao.movieInsert(movie);
            view = "redirect:/?pageNum=1";
            msg = "�ۼ� ����";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:writeFrm";
            msg = "�ۼ� ����";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    private void fileUpload(List<MultipartFile> files,
                            HttpSession session,
                            MovieDTO movie) throws Exception {
        log.info("fileUpload()");
        String sysname = null;
        String oriname = null;

        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/";
        log.info(realPath);
        File folder = new File(realPath);
        if(!folder.isDirectory()){//������ ���� ��� ����.
            folder.mkdir();//���� ���� �޼ҵ�
        }

        MultipartFile mf = files.get(0);
        oriname = mf.getOriginalFilename();//���ε� ���ϸ� ��������

        sysname = System.currentTimeMillis()
                + oriname.substring(oriname.lastIndexOf("."));
        //���ε��ϴ� ������ upload ������ ����.
        File file = new File(realPath + sysname);
        //���� ����(upload ����)
        mf.transferTo(file);

        movie.setP_oriname(oriname);
        movie.setP_sysname(sysname);
    }

    public ModelAndView getMovie(Integer m_code) {
        log.info("getMovie()");
        mv = new ModelAndView();

        MovieDTO movie = mDao.movieSelect(m_code);

        mv.addObject("movie", movie);
        return mv;
    }

    public String movieUpdate(List<MultipartFile> files,
                              MovieDTO movie,
                              HttpSession session,
                              RedirectAttributes rttr) {
        log.info("movieUpdate()");
        String msg = null;
        String view = null;
        String poster = movie.getP_sysname();
        String upFile = files.get(0).getOriginalFilename();

        try {
            if(!upFile.equals("")) {
                fileUpload(files, session, movie);
            }
            mDao.movieUpdate(movie);

            if(poster != null && !upFile.equals("")) {
                fileDelete(poster, session);
            }

            view = "redirect:detail?m_code="+movie.getM_code();
            msg = "���� ����";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:updateFrm?m_code="+movie.getM_code();
            msg = "���� ����";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    private void fileDelete(String poster,
                            HttpSession session) throws Exception{
        log.info("fileDelete()");

        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/" + poster;
        File file = new File(realPath);
        if(file.exists()){
            file.delete();//������ ������ ����
        }
    }

    public String deleteMovie(Integer m_code,
                              HttpSession session,
                              RedirectAttributes rttr) {
        String msg = null;
        String view = null;
        String poster = null;

        try {
             poster = mDao.getPoster(m_code);
             if(poster != null && !poster.equals("")) {
                 fileDelete(poster, session);
             }
             mDao.movieDelete(m_code);
             view = "redirect:/?pageNum=1";
        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:detail?m_code="+m_code;
            msg = "���� ����";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }
}