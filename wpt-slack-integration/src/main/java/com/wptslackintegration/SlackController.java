package com.wptslackintegration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.SlackApiException;

@RestController
@Component
public class SlackController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SlackService slackService;

	@PostMapping(value = "/submittest", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Url getPerfDetail(@RequestBody Url address)
			throws JsonMappingException, JsonProcessingException, InterruptedException {

		String url = "https://www.webpagetest.org/runtest.php?url=" + address.getUrl() + "&k=" + address.getKey()
				+ "&location=" + address.getLocation() + "&fvonly=1&f=json"
				+ "&pingback=ngrokUrl/testresult";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode data = mapper.readTree(response.getBody());

		return null;

	}

	@GetMapping(value = "/testresult")
	@ResponseBody
	public void getData(HttpServletRequest req) throws IOException, SlackApiException {
		String jsonUrl = "https://www.webpagetest.org/jsonResult.php?test=" + req.getParameter("id");
		ResponseEntity<String> res = restTemplate.getForEntity(jsonUrl, String.class);
		ObjectMapper map = new ObjectMapper();
		try {
			JsonNode printData = map.readTree(res.getBody());
			slackService.sendMsgtoSlack(printData, req);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
