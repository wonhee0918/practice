package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {


    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");


    }
    
    @ResponseBody 
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        
        return "ok"; 
        // @RestController가 아니라 @Controller일 경우 return "ok"를 하면 ok이라는 이름의 뷰를 찾아서 반환하게됨.
        // 그래서 @Controller를 @RestController로 변경하거나, @ResponseBody을 메서드 위에 추가하면 "ok"를 Http메시지 바디에 실어줌.
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

 /* required 사용 */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam String username, //required가 true일때 null은 안되지만 ""은 가능. 그래서 "username=" 만 입력해도 그냥 들어옴 
                                       @RequestParam(required = false) Integer age) {//required(기본값 필수 유무). 없으면 true자동설정
    //@RequestParam(required = false) int age로 하면 파라미터 없을 시 null이 들어가서 컴파일 오류남(int는 0이라도 꼭 들어가야 하기 때문에. 그래서 객체형인 Integer 사용 OR defaultValue사용(다음 메서드 참고)
        log.info("username={}, age={}", username, age);

        return "ok";
    }
/* required 의 defaultValue 사용 */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamRequired(@RequestParam(defaultValue = "guest") String username, //defaultValue가 설정되어 있으면 "username="처럼 아무것도 입력 안할 시에도 설정한 기본값으로 해줌
                                       @RequestParam(required = false, defaultValue = "-1") int age) {//사실 상 defaultValue쓰면 required는 지워도 됨.

        log.info("username={}, age={}", username, age);

        return "ok";
    }
/* @RequestParam 모두를 맵으로 받기*/
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, String> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }
/* @ModelAttribute 사용 */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {


        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        log.info("helloData={},",helloData);

        return "ok";
    }

/* @ModelAttribute 생략해서 사용 */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {


        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        log.info("helloData={},",helloData);

        return "ok";
    }


/*
* 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
String , int , Integer 같은 단순 타입 = @RequestParam
나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
**/

}
