package com.basic.crud.basic_crud.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/temp")
class TempController {

    @GetMapping("/greet")
    fun greet(@RequestParam("name") name: String?): String {
        return "Hello Request Param, $name!"
    }

    @GetMapping("/users/{id}")
    fun user(@PathVariable("id") id: Long): String {
        return "Hello Path Variable, $id!"
    }
}