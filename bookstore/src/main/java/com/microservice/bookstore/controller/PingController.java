package com.microservice.bookstore.controller;

import com.microservice.bookstore.response.StatusResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@Api(value = "Ping Controller", description = "Controller to check server status")
public class PingController {

    @GetMapping(value = "/status")
    @ApiOperation(value = "Check status", notes = "This endpoint provides server status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Server status okay",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = StatusResponse.class))}),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)}
    )
    public ResponseEntity<StatusResponse> getStatus() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StatusResponse("Okay", null));
    }
}
