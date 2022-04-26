package com.example.account_final;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    //@Email //이게 id
    private String email; //어노테이션이랑 겹쳐서 EEmail로 함

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String companyName; //유저 회사 이름

    private String companyNumber; //사업자 등록번호

    private boolean emailVerified;

    @JsonIgnore
    private String emailCheckToken;


    public void generateEmailCheckToken() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        uuid = uuid.substring(0, 10);
        this.emailCheckToken = uuid;
        //this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSignUp() {
        this.setEmailVerified(true);

    }
}
