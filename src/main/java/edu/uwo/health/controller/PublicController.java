package edu.uwo.health.controller;

import edu.uwo.health.entity.ResponseEntity;
import edu.uwo.health.entity.User;
import edu.uwo.health.service.UserService;
import edu.uwo.health.utils.RSAUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Public", tags = "Public")
@RestController
@RequestMapping(value = "/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "key")
    @GetMapping("/publicKey")
    public ResponseEntity getPublicKey(@ApiParam(value = "username", required=true) @Valid @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            String publicKey = RSAUtils.getPublicKey();
            return ResponseEntity.success(publicKey);
        } else {
            return ResponseEntity.fail(ResponseEntity.NOT_FOUND, "username not found");
        }
    }

}
