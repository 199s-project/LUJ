package com.example.demo.Contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MovieDTO;
import com.example.demo.service.MovieService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class demoController {

	private ModelAndView mv;

	@Autowired
    private MovieService mServ;

	@GetMapping("/")
    public ModelAndView getList(){
        log.info("getList()");
        mv = mServ.getMovieList();
        return mv;
    }


	@GetMapping("writeFrm")
	public String writeFrm(){
		log.info("writeFrm()");
		return "writeFrm";
	}

	@PostMapping("writeProc")
	public String writeProc(@RequestPart("files") List<MultipartFile> files,
	                            MovieDTO movie,
	                            HttpSession session,
	                            RedirectAttributes rttr) {
	    log.info("writeProc()");
	    String view = mServ.insertMovie(files, movie, session, rttr);

	    return view;
	}

	@GetMapping("detail")
	public ModelAndView detail(@RequestParam("m_code") Integer m_code){
	    log.info("detail()");
	    mv = mServ.getMovie(m_code);
	    mv.setViewName("detail");
	    return mv;
	}

	@GetMapping("updateFrm")
	public ModelAndView updateFrm(@RequestParam("m_code") Integer m_code){
	    log.info("updateFrm()");
	    mv = mServ.getMovie(m_code);
	    mv.setViewName("updateFrm");
	    return mv;
	}

	@PostMapping("updateProc")
	public String updateProc(@RequestPart("files") List<MultipartFile> files,
	                         MovieDTO movie,
	                         HttpSession session,
	                         RedirectAttributes rttr){
	    log.info("updateProc()");
	    String view = mServ.movieUpdate(files, movie, session, rttr);

	    return view;
	}

	@GetMapping("delete")
	public String deleteProc(@RequestParam("m_code") Integer m_code,
	                         HttpSession session,
	                         RedirectAttributes rttr){
	    log.info("deleteProc()");
	    String view = mServ.deleteMovie(m_code, session, rttr);

	    return view;
	}
}
