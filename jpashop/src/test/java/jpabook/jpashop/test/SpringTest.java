package jpabook.jpashop.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SpringBootTest
class SpringTest {
	@Test
	void test() {
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.trace("trace");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}
	@Test
	void contextLoads() throws Exception{
		String key = "A";

		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		Map<String, Object> payloads = new HashMap<>();
		int expired = 36000;
		Date now = new Date();
		now.setTime(now.getTime() + expired);
		payloads.put("exp", now);
		payloads.put("data", "awdawd");

		String jwt = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, key.getBytes())
				.compact();
		System.out.println(jwt);
	}

}
