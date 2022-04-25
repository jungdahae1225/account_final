package com.example.account_final;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;
    private final ModelMapper modelMapper;

    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }

    @PostMapping("/mail")
    public void execMail(MailDto mailDto) {
        //1.이미 있는 이메일인가 확인 if문 통과 하면 중복 없는 것
        //if (MailService.checkEmailDuplicate(mailDto.getAddress())) {
            //return ResponseEntity.badRequest().build(); //@@중복 Response로 바꿔주기-프론트랑 논의
        //}

        Account account = modelMapper.map(mailDto, Account.class);

        mailService.sendEmailCheckToken(account);

        mailService.mailSend(mailDto);
    }
}