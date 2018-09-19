package tu.tai;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/test")
public class TestController {

    private Queue<String> messages = new ConcurrentLinkedQueue<>();

    @RequestMapping(
            path = "get_test",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity get_test() {

        String responseBody = "From get_test!";
        return ResponseEntity.ok(responseBody);
    }

    @RequestMapping(
            path = "post_test",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity post_test(@RequestParam("name") String name) {

        messages.add(name);

        String responseBody = String.join("\n", messages.stream().sorted().collect(Collectors.toList()));

        return ResponseEntity.ok(responseBody);
    }
}
