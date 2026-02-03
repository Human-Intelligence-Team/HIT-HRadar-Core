package org.hit.hradar.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "api")
@Aspect
@Component
public class ControllerLogging {

  @Around("within(@org.springframework.web.bind.annotation.RestController *)")
  public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {

    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();

    String uri = request.getRequestURI();
    String method = request.getMethod();
    Object[] args = joinPoint.getArgs();

    long start = System.currentTimeMillis();
    log.info("[REQUEST] {} {} args={}", method, uri, Arrays.toString(args));

    Object result = joinPoint.proceed();

    long elapsed = System.currentTimeMillis() - start;
    log.info("[RESPONSE] {} {} return={} ({} ms)", method, uri, result, elapsed);

    return result;
  }
}