package org.hit.hradar.global.logging;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLogging {

  @Around("within(@org.springframework.stereotype.Service *)")
  public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {

    String method = joinPoint.getSignature().toShortString();
    Object[] args = joinPoint.getArgs();

    long start = System.currentTimeMillis();
    log.info("[SERVICE CALL] {} args={}", method, Arrays.toString(args));

    Object result = joinPoint.proceed();

    long elapsed = System.currentTimeMillis() - start;
    log.info("[SERVICE RETURN] {} result={} ({} ms)", method, result, elapsed);

    return result;
  }
}