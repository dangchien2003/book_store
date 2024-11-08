package org.example.identityservice.repository.httpClient;


import org.example.identityservice.dto.response.GoogleUserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "google-auth-profile", url = "https://www.googleapis.com")
public interface GoogleProfileClient {
    @GetMapping(value = "/oauth2/v3/userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    GoogleUserProfileResponse getProfile(@RequestHeader(name = "Authorization") String accessToken);
}
