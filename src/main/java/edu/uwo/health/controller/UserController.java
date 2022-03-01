package edu.uwo.health.controller;

import edu.uwo.health.entity.*;
import edu.uwo.health.service.EmailService;
import edu.uwo.health.service.TempUserService;
import edu.uwo.health.service.UserService;
import edu.uwo.health.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static edu.uwo.health.entity.ResponseEntity.*;


@Api(value = "User", tags = "User")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private TempUserService tempUserService;

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "user login")
    @PostMapping("/login")
    public ResponseEntity Login(@ApiParam(value = "user login data", required=true)@Valid @RequestBody LoginParams params,
            HttpServletResponse response
    ) {
        org.springframework.security.core.userdetails.User loginUser =
                userService.getUserByUsernameAndPassword(params.getUsername(), params.getPassword());
        if (loginUser == null) {
            return ResponseEntity.fail(ResponseEntity.EXCEPTION, "invalid username or password");
        }

        User findUser = userService.getUserByUsername(loginUser.getUsername());
        String token = jwtUtils.createToken(loginUser.getUsername(), loginUser.getUsername());

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("username", loginUser.getUsername());
        respMap.put("name", findUser.getName());
        respMap.put("healthStatus", findUser.getHealthStatus());
        respMap.put("studentID", findUser.getStudentID());
        respMap.put("AutoLoginTicket", token);

        response.addHeader("Authorization", "Bearer " + token);

        return ResponseEntity.success(respMap);
    }

    @ApiOperation(value = "user register")
    @PostMapping("/register")
    public ResponseEntity Register(
            @ApiParam(value = "user data" , required=true)@Valid @RequestBody RegisterParams params
    ) {
        TempUser findTempUser = tempUserService.getTempUserByUsername(params.getUsername());

        Map<String, Boolean> respMap = new HashMap<>();

        if (findTempUser == null) {
            respMap.put("verifySuccess", false);
            return ResponseEntity.fail(NOT_FOUND, "username does not exist", respMap);
        }

        if (!findTempUser.getVerifyCode().equals(params.getVerifyCode())) {
            respMap.put("verifySuccess", false);
            return ResponseEntity.fail(PARAMS_ERROR, "invalid verifyCode", respMap);
        }

        User user = new User();
        user.setUsername(findTempUser.getUsername());
        user.setPassword(findTempUser.getPassword());
        user.setName(findTempUser.getName());
        user.setStudentID(findTempUser.getStudentID());
        userService.createUser(user);
        tempUserService.deleteTempUser(user.getUsername());

        respMap.put("verifySuccess", true);
        return ResponseEntity.success(respMap);
    }

    @ApiOperation(value = "user face login")
    @PostMapping("/faceLogin")
    public ResponseEntity FaceLogin(@ApiParam(value = "face login data", required=true) @Valid @RequestBody FaceLoginParams params) {
        User findUser = userService.getUserByUsername(params.getUsername());

        if (findUser == null) {
            return ResponseEntity.fail(NOT_FOUND, "username does not exist");
        }

        String token = jwtUtils.createToken(findUser.getUsername(), findUser.getUsername());

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("username", findUser.getUsername());
        respMap.put("name", findUser.getName());
        respMap.put("healthStatus", findUser.getHealthStatus());
        respMap.put("studentID", findUser.getStudentID());
        respMap.put("AutoLoginTicket", token);

        return ResponseEntity.success(respMap);
    }

    @ApiOperation(value = "user auto login")
    @PostMapping("/autoLogin")
    public ResponseEntity AutoLogin(@ApiParam(value = "auto login data", required=true) @Valid @RequestBody AutoLoginParams params) {
        User findUser = userService.getUserByUsername(params.getUsername());

        if (findUser == null) {
            return ResponseEntity.fail(NOT_FOUND, "username does not exist");
        }

        Claims claims = jwtUtils.parseToken(params.getAutoLoginTicket());
        String audience = claims.getAudience();

        if (audience.equals(findUser.getUsername())) {
            Map<String, Object> respMap = new HashMap<>();
            respMap.put("username", findUser.getUsername());
            respMap.put("name", findUser.getName());
            respMap.put("healthStatus", findUser.getHealthStatus());
            respMap.put("studentID", findUser.getStudentID());

            return ResponseEntity.success(respMap);
        } else {
            return ResponseEntity.fail(PARAMS_ERROR, "invalid ticket");
        }
    }

    @ApiOperation(value = "user report health")
    @PostMapping("/reportHealth")
    public ResponseEntity ReportHealth(@ApiParam(value = "report health data", required=true) @Valid @RequestBody ReportHealthParams params) {
        String username = params.getUsername();
        int healthstatus = params.getHealthstatus();

        User findUser = userService.getUserByUsername(username);

        if (findUser == null) {
            return ResponseEntity.fail(NOT_FOUND, "username does not exist");
        }

        if (healthstatus == HealthCode.GREEN.getValue() ||
                healthstatus == HealthCode.YELLOW.getValue() ||
                healthstatus == HealthCode.RED.getValue()) {
            if (findUser.getUpdateDate() != null && isToday(findUser.getUpdateDate())) {
                return ResponseEntity.fail(REPORT_ALREADY, "report already");
            }
            userService.updateUser(username, healthstatus);
            return ResponseEntity.success(null);
        } else {
            return ResponseEntity.fail(INVALID_PARAMS, "invalid health code");
        }
    }

    @ApiOperation(value = "register send email")
    @PostMapping("/sendEmail")
    public ResponseEntity SendEmail(@ApiParam(value = "user info" , required=true)@Valid @RequestBody EmailUser emailUser) {
        TempUser tempUser = new TempUser();
        tempUser.setUsername(emailUser.getUsername());
        String bCryptPassword = bCryptPasswordEncoder.encode(emailUser.getPassword());
        tempUser.setPassword(bCryptPassword);
        tempUser.setName(emailUser.getName());
        tempUser.setStudentID(emailUser.getStudentID());

        User findUser = userService.getUserByUsername(tempUser.getUsername());
        if (findUser != null) {
            return ResponseEntity.fail(PARAMS_ERROR, "username has been registered");
        }
        TempUser findTempUser = tempUserService.getTempUserByUsername(tempUser.getUsername());
        String verifyCode = getRandomNumberString();
        tempUser.setVerifyCode(verifyCode);
        if (findTempUser == null) {
            tempUserService.createTempUser(tempUser);
        } else {
            tempUserService.updateTempUser(tempUser.getUsername(), tempUser.getVerifyCode());
        }

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("username", tempUser.getUsername());

        try {
            emailService.sendSimpleMessage(tempUser.getUsername(), tempUser.getVerifyCode());
            respMap.put("emailSend", true);
            return ResponseEntity.success(respMap);
        } catch (Exception e) {
            System.out.println(e);
            respMap.put("emailSend", false);
            return ResponseEntity.fail(FAIL, "send email fail", respMap);
        }
    }

    private boolean isToday(Date date) {
        String PATTEN_DEFAULT_YMD = "yyyy-MM-dd";
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(PATTEN_DEFAULT_YMD);
        String nowDay = sf.format(now);
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    private static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
