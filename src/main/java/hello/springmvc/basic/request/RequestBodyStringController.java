package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. InputStream으로 request body를 읽어옴
        ServletInputStream inputStream = request.getInputStream();
        // 2. inputStream을 UTF-8 문자열로 변환
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("ok");


    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        responseWriter.write("ok");

    }

/* HttpEntity 사용
*  HttpEntity: HTTP header, body 정보를 편리하게 조회
    메시지 바디 정보를 직접 조회
    요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X    
 */
    //HttpEntity<String> httpEntity =>Http request body, header에 있는 것을 String으로 바꿔서 넣어줄게
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();

        log.info("messageBody={}",messageBody);
        log.info("headers={}",headers);

//        return new HttpEntity<>("ok"); //HttpEntity는 응답에도 사용 가능(메시지 바디 정보 직접 반환,헤더 정보 포함 가능,view 조회X)
        return new ResponseEntity<>("ok",HttpStatus.CREATED);
    }
    
/* RequestEntity 사용*/
/*HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
        RequestEntity
        HttpMethod, url 정보가 추가, 요청에서 사용
        
        ResponseEntity
        HTTP 상태 코드 설정 가능, 응답에서 사용*/    
    @PostMapping("/request-body-string-v3-requestEntity")
    public HttpEntity<String> requestBodyStringV3RequestEntity(RequestEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();

        log.info("messageBody={}",messageBody);

        return new ResponseEntity<>("ok",HttpStatus.CREATED);
    }

/* @RequestBody(요청 오는거.but 헤더정보는 X), @ResponseBody(응답 하는거)로 더 간단하게 */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}",messageBody);

        return "ok";

    }





}
