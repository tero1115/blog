package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping({ "/", "/board" })
    public String main(Model model) {
        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "board/main";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("dto", boardRepository.findByIdWithUser(id));
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board")
    public @ResponseBody ResponseEntity<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        if (boardSaveReqDto.getTitle() == null || boardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title??? ??????????????????");
        }
        if (boardSaveReqDto.getContent() == null || boardSaveReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content??? ??????????????????");
        }
        if (boardSaveReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title??? ????????? 100??? ???????????? ?????????");
        }

        boardService.?????????(boardSaveReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "???????????????", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {// @ResponseBody????????? ???????????? ?????????
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        boardService.???????????????(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "????????????", null), HttpStatus.OK);
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomException("?????? ???????????? ????????? ??? ????????????");
        }
        if (boardPS.getUserId() != principal.getId()) {
            throw new CustomException("???????????? ????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }

        model.addAttribute("board", boardPS);
        return "board/updateForm";
    }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        if (boardUpdateReqDto.getTitle() == null || boardUpdateReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title??? ??????????????????");
        }
        if (boardUpdateReqDto.getContent() == null || boardUpdateReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content??? ??????????????????");
        }
        if (boardUpdateReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title??? ????????? 100??? ???????????? ?????????");
        }
        boardService.???????????????(id, boardUpdateReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "?????????????????????", null), HttpStatus.CREATED);
    }

}
