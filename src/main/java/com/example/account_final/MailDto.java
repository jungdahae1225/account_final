package com.example.account_final;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;
}