package com.example.account_final.event;

import com.example.account_final.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpConfirmEvent {
    private final Account account;
}
