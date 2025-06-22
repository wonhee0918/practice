package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//메서드에서 return "ok"할 때 그냥 @Controller는 ok라는 뷰를 반환.
//@Restcontroller는 "ok"라는 문자열을 반환(http메세지 바디에 문자열을 박아버림.restAPI만들때 주로 사용)
@RestController
@Slf4j //롬복이 private  final Logger log = LoggerFactory.getLogger(getClass()); 이거 자동으로 해주는거
public class LogTestController {

    //@Slf4j 에노테이션 사용하면 이거 자동으로 해줌
    //private  final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("LogTestController.logTest");
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}",name);
        log.warn("warn log={}",name);
        log.error("error log={}",name);

        return "ok";
    }
}
