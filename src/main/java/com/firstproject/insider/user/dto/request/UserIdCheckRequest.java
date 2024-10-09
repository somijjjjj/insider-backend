package com.firstproject.insider.user.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class UserIdCheckRequest {

    @NonNull
    private String userId;
}
