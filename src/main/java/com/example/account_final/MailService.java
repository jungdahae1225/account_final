package com.example.account_final;

import com.example.account_final.event.SignUpConfirmEvent;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MailService {
    private AccountRepository accountRepository;
    private JavaMailSender mailSender;
    private final ApplicationEventPublisher publisher;
    private static final String FROM_ADDRESS = "dahaeSpringstudy@gmail.com";

    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        //message.setSubject(mailDto.getTitle());
        //message.setText(mailDto.getMessage());

        message.setSubject("CollaBiz 서비스 사용을 위해 코드를 복사하여 붙여넣어주세요.");
        message.setText("안녕하세요 CollaBiz입니다");
        mailSender.send(message);
    }

    public void sendEmailCheckToken(Account account) {//Account account
        //우리는 저장 되어 있는 엔티티를 찾아서 다시 토큰 주는게 아니라 아예 처음 만드는 것이므로, 원래 있던 3줄 코드 필요 없다.

        //1.토큰을 만들고 generateEmailCheckToken에서 Account 엔티티의 토큰에 값을 넣어준다.
        account.generateEmailCheckToken();
        //2.이메일을 보낸다
        sendConfirmEmail(account);
    }
    // send emailCheckToken
    private void sendConfirmEmail(Account account) {
        //SignUpConfirmEvent의 EEmail을 이벤트에 쓸 객체로 대체해 이메일 전송 이벤트를 처리한다.
        publisher.publishEvent(new SignUpConfirmEvent(account));
    }

    //create 220411 dahae
    //public static boolean checkEmailDuplicate(String email) {
    //    return accountRepository.existsByEmail(email);
    //}

    public void CollmailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject("CollaBiz 서비스 사용을 위해 코드를 복사하여 붙여넣어주세요.");
        message.setText("안녕하세요 CollaBiz입니다");

        mailSender.send(message);
    }
}