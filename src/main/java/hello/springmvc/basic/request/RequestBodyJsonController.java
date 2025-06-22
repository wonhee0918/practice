package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    // JSON 문자열을 객체로 변환해주는 Jackson ObjectMapper
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody =  StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        
        log.info("messageBody={}",messageBody);

        // 3. JSON 문자열을 HelloData 객체로 변환 (역직렬화)
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws Exception {

        log.info("messageBody={}",messageBody);

        // 3. JSON 문자열을 HelloData 객체로 변환 (역직렬화)
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws Exception {
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> helloData) throws Exception {
        //HttpEntity는 바디를 꺼내줘야함
        HelloData data = helloData.getBody();
        log.info("username={}, age={}",data.getUsername(),data.getAge());

        return "ok";
    }

    //@ResponseBody 가 있으면 문자를 return했을 때 view반환안하고 바디에 문자를 그대로 실었듯이
    //HelloData 를 return해도 바디에 실어줌
    //HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept:
    //    application/json)
    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
    application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept:
    application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws Exception {
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());

        return helloData;
    }
}
