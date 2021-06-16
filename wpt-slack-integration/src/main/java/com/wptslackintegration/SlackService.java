package com.wptslackintegration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

@Service
public class SlackService { 

	@Value(value = "${bottoken}")
	private String botToken;

	@Value(value = "${channelid}")
	private String channelId;

	public void sendMsgtoSlack(JsonNode printData, HttpServletRequest req) {

		String testDetails = "https://www.webpagetest.org/results.php?test=" + req.getParameter("id");

		String requestedUrl = printData.get("data").get("testUrl").asText();
		String str = "[\r\n" + "        {\r\n" + "          \"type\": \"header\",\r\n" + "          \"text\": {\r\n"
				+ "            \"type\": \"plain_text\",\r\n" + "            \"text\": \"WebPageTest Details\",\r\n"
				+ "            \"emoji\": true\r\n" + "          }\r\n" + "        },\r\n" + "        {\r\n"
				+ "          \"type\": \"section\",\r\n" + "          \"text\": {\r\n"
				+ "            \"type\": \"mrkdwn\",\r\n" + "            \"text\": \"*Tested URL*\"\r\n"
				+ "          },\r\n" + "          \"accessory\": {\r\n" + "            \"type\": \"button\",\r\n"
				+ "            \"text\": {\r\n" + "              \"type\": \"plain_text\",\r\n"
				+ "              \"text\": \"URL Link\",\r\n" + "              \"emoji\": true\r\n"
				+ "            },\r\n" + "            \"value\": \"click_me_123\",\r\n" + "            \"url\":   \""
				+ requestedUrl + "\",\r\n" + "            \"action_id\": \"button-action\"\r\n" + "          }\r\n"
				+ "        },\r\n" + "        {\r\n" + "          \"type\": \"section\",\r\n"
				+ "          \"text\": {\r\n" + "            \"type\": \"mrkdwn\",\r\n"
				+ "            \"text\": \"*Test Details*\"\r\n" + "          },\r\n" + "          \"accessory\": {\r\n"
				+ "            \"type\": \"button\",\r\n" + "            \"text\": {\r\n"
				+ "              \"type\": \"plain_text\",\r\n" + "              \"text\": \"WebPageTest\",\r\n"
				+ "              \"emoji\": true\r\n" + "            },\r\n"
				+ "            \"value\": \"click_me_123\",\r\n" + "            \"url\":   \"" + testDetails + "\",\r\n"
				+ "            \"action_id\": \"button-action\"\r\n" + "          }\r\n" + "        }\r\n" + "      ]";

		Slack slack = Slack.getInstance();

		try {
			ChatPostMessageResponse response = slack.methods(botToken)
					.chatPostMessage(p -> p.channel(channelId).blocksAsString(str));
			if (response.isOk()) {
				System.out.println("success");
			} else {
				String err = response.getError();
				System.out.println(err);
			}

			response.getChannel();
		} catch (IOException e) {
			System.out.println("hello");
			e.printStackTrace();
		} catch (SlackApiException e) {
			e.printStackTrace();
		}

	}

}
