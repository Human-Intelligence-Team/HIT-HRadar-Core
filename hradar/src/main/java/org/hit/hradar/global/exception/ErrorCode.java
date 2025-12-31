package org.hit.hradar.global.exception;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {

  String getErrorCode();

  String getMessage();

  HttpStatusCode getHttpStatusCode();
}
