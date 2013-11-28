package com.agileEAP.debug;

import java.util.UUID;

import org.springframework.util.LinkedMultiValueMap;  
import org.springframework.util.MultiValueMap;  
import org.springframework.web.client.RestTemplate;  

  
public class RestTemplateTest {  
      
    public static void main(String[] args) {  
        RestTemplate restTemplate = new RestTemplate();  
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();  
        //form.add("salary", "2,500.00");  
/*        Eartag eartag = new Eartag();
		eartag.setEarid(UUID.randomUUID().toString());
		eartag.setEarcode("a");
		eartag.setCompid("b");
		eartag.setCompname("c");
		eartag.setOperatorcode("d");
		eartag.setOperatorname("e");
		eartag.setManagerdepart("f");
		eartag.setCorralcode("g");
		eartag.setCorralname("h");
		eartag.setFeedstatus("i");
		eartag.setAnimalcode("j");
		eartag.setAnimalname("k");
		eartag.setRemark("l");
        String response = restTemplate.postForObject(  
                "http://localhost:8080/eTrace-portal/cxf/jaxrs/breed/eartag/api/add", 
                eartag, String.class);  
        System.out.println("response:\n" + response);  */
    }  
      
}  
