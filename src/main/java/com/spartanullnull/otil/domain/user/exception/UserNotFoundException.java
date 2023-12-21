package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String field, String value, String reason) {
        super(ErrorCode.of(ErrorCase.USER_NOT_FOUND), new ErrorDetail(field, value, reason, LocalDateTime.now()));
    }

    public UserNotFoundException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.USER_NOT_FOUND),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
