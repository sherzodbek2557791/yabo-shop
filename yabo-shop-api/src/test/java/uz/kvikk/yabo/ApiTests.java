package uz.kvikk.yabo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import uz.kvikk.yabo.utils.exchange.TestExchange;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTests {

    @Autowired
    TestExchange testExchange;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        testExchange.convertAndSend(new HashMap<String, Object>() {{
            put("test", "Hello world test");
        }});
    }

    @Test
    public void restTest() {
//        http://10.0.23.31:8096/api/v1/generate-document-out-number
        ResponseEntity<String> entity = restTemplate.postForEntity("http://10.0.23.31:8096/api/v1/generate-document-out-number", new HashMap<String, Object>(){{}}, String.class);
        if(entity.getStatusCode() == HttpStatus.OK)
            System.out.println(entity.getBody());
    }


}

