package io.mosip.admin.util;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminDataUtil {

	public static void checkResponse(MvcResult rst, String expectedCode) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (rst.getResponse().getContentAsString().isEmpty() && rst.getResponse().getStatus() == 404) {
				assertEquals(404, rst.getResponse().getStatus());

			} else {
				Map m = mapper.readValue(rst.getResponse().getContentAsString(), Map.class);
				assertEquals(200, rst.getResponse().getStatus());
				if (m.containsKey("errors") && null != m.get("errors")) {
//					assertEquals(((List<Map<String, String>>) m.get("errors")).get(0).get("errorCode"), actualCode);
					assertEquals(expectedCode, ((List<Map<String, String>>) m.get("errors")).get(0).get("errorCode"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void checkErrorResponse(MvcResult rst, String s) {
		assertEquals(rst.getResponse().getStatus(), 500);
	}

}