package com.example.account_final;

import com.example.account_final.dtos.AccountResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
//@RequiredArgsConstructor
@AllArgsConstructor
public class MailService {
    private AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    //mailSender는 AllArgsConstructor가 필요함
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "dahaeSpringstudy@gmail.com";

    public void mailSend(Account account) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(account.getEmail());
        message.setFrom(MailService.FROM_ADDRESS);
        //message.setSubject(mailDto.getTitle());
        //message.setText(mailDto.getMessage());

        message.setSubject("CollaBiz 서비스 사용을 위해 코드를 복사하여 붙여넣어주세요.");
        message.setText("안녕하세요 CollaBiz입니다 다음 문자를 홈페이지에 입력해주세요 => " + account.getEmailCheckToken());
        mailSender.send(message);
    }

    public void sendEmailCheckToken(Account account) {//Account account
        //우리는 저장 되어 있는 엔티티를 찾아서 다시 토큰 주는게 아니라 아예 처음 만드는 것이므로, 원래 있던 3줄 코드 필요 없다.

        //1.토큰을 만들고 generateEmailCheckToken에서 Account 엔티티의 토큰에 값을 넣어준다.
        account.generateEmailCheckToken();
        //2.이메일을 보낸다
        //sendConfirmEmail(account);
        mailSend(account);
    }

    //create 220411 dahae
    //public static boolean checkEmailDuplicate(String email) {
    //    return accountRepository.existsByEmail(email);
    //}

    /**
     * 이메일 토큰 검증 로직
     */
    public AccountResponseDto emailVerification(Account account, String token){
        if (!account.isValidToken(token)) {
            return null;
        }

        completeSignUp(account);
        return createAccountResponseDto(account);
    }
    public void completeSignUp(Account find) {
        find.completeSignUp();
    }

    public AccountResponseDto createAccountResponseDto(Account account){
        AccountResponseDto dto = new AccountResponseDto();
        dto.setEmail(account.getEmail());
        dto.setEmailVerified(account.isEmailVerified());
        return dto;
    }

    /**
     * 회원가입 정보 저장
     */
    // save account
    public Account saveNewAccount(AccountDto accountDto) {
        Account map = modelMapper.map(accountDto, Account.class);
        //map.setPassword(passwordEncoder.encode(map.getPassword()));
        //map.generateEmailCheckToken(); 이메일 토큰 처리는 분리해 주었으니 이제 없어도 된다.
        Account saved = accountRepository.save(map);
        return saved;
    }
}