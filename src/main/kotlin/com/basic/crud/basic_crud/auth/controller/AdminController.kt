package com.basic.crud.basic_crud.auth.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
) {

    @GetMapping("/test")
    fun test(): String {
        return "Hello admin user"
    }
}