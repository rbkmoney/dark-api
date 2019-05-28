package com.rbkmoney.dark.api.auth;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 17.11.16
 **/

@RestController
public class TestRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("Операция закрытая просто валидированным JWT")
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping(@RequestHeader(value = "Authorization") String auth) {
        return "pong";
    }

    @ApiOperation(value = "Операция закрытая ролью", notes = "Требует роль 'RBKadmin' ")
    @PreAuthorize("hasAuthority('RBKadmin')")
    @RequestMapping(value = "/testAdmin", method = RequestMethod.GET)
    public String testAdmin(@RequestHeader(value = "Authorization") String auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getAuthorities().toString());
        return "testAdmin!";
    }

    @ApiOperation(value = "Операция закрытая ролью менеджер", notes = "требует роль 'RBKmanager' ")
    @PreAuthorize("hasAuthority('RBKmanager')")
    @RequestMapping(value = "/testManager", method = RequestMethod.GET)
    public String testManager(@RequestHeader(value = "Authorization") String auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getAuthorities().toString());
        return "testManager!";
    }

}
