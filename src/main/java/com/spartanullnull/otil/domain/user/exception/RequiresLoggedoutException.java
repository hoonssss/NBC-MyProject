package com.spartanullnull.otil.domain.user.exception;

import com.spartanullnull.otil.domain.common.exception.*;
import com.spartanullnull.otil.global.exception.entity.*;
import java.time.*;

public class RequiresLoggedoutException extends DomainException {

    public RequiresLoggedoutException(String field, String value, String reason) {
        super(
            ErrorCode.of(ErrorCase.REQUIRES_LOGGED_OUT),
            new ErrorDetail(field, value, reason, LocalDateTime.now()
            )
        );
    }

    public RequiresLoggedoutException(String field, String value) {
        super(
            ErrorCode.of(ErrorCase.REQUIRES_LOGGED_OUT),
            new ErrorDetail(field, value, LocalDateTime.now()
            )
        );
    }
}
