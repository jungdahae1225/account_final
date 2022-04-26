package com.example.account_final;

import com.example.account_final.dtos.AccountResponseDto;
import com.example.account_final.errors.ErrorResource;
import com.example.account_final.validator.SignUpFormValidator;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;
    private final SignUpFormValidator validator;

    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }

    /**
     * 이메일 인증번호 전송 로직
     */
    @PostMapping("/mail")
    public void execMail(MailDto mailDto) {
        //1.이미 있는 이메일인가 확인 if문 통과 하면 중복 없는 것
        //if (MailService.checkEmailDuplicate(mailDto.getAddress())) {
            //return ResponseEntity.badRequest().build(); //@@중복 Response로 바꿔주기-프론트랑 논의
        //}

        Account account = new Account();
        account.setEmail(mailDto.getAddress());
        mailService.sendEmailCheckToken(account);

        mailService.mailSend(account);
    }


    /**
     * 이메일 토큰 검증 로직
     */
    @PostMapping("/emailVerification")
    public ResponseEntity emailVerification(@CurrentUser Account account, @RequestParam String token){

        //현재 인증 받고 있는 유저를 저장 하여 들고다닌다. @CurrentUser를 사용해서 가지고 온다.
        AccountResponseDto dto = mailService.emailVerification(account, token);
        if(dto == null){
            return ResponseEntity.badRequest().build();
        } // 인증번호 맞지 않음

        return ResponseEntity.ok(dto); //인증 번호 맞으면
    }

    /**
     * 최종 회원가입
     */

    @PostMapping("/signUp") //이메일 인증 완료 후 회원가입 완료 버튼
    public ResponseEntity signUp(@RequestBody AccountDto accountDto, Errors errors) {
        if (errors.hasErrors()) {
            EntityModel<Errors> jsr303error = ErrorResource.modelOf(errors);
            return ResponseEntity.badRequest().body(jsr303error);
        }
        validator.validate(accountDto, errors);
        if (errors.hasErrors()) {
            EntityModel<Errors> customError = ErrorResource.modelOf(errors);
            return ResponseEntity.badRequest().body(customError);
        }
        Account account = mailService.saveNewAccount(accountDto); //회원가입(accountRepository.save())
        EntityModel<Account> accountResource = AccountResource.modelOf(account);
        return ResponseEntity.ok(accountResource);
    }
}