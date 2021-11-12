package com.github.marcindabrowski.example.roomsbooking.infrastructure.ability

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

trait RestApiAbility {
    String BOOK_ROOMS_ENDPOINT_PATH = "/book-rooms"

    @Autowired
    TestRestTemplate restTemplate

    ResponseEntity<Map> bookRooms(Map<String, Object> payloadAsJson) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map>(payloadAsJson, headers)
        return restTemplate.postForEntity(BOOK_ROOMS_ENDPOINT_PATH, request, Map)
    }
}
