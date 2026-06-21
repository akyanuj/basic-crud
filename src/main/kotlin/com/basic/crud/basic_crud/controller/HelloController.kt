package com.basic.crud.basic_crud.controller

import com.basic.crud.basic_crud.model.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): Greeting {
//        return "Hello fucking World! i have arrived in the world of spring boot"
      /*  return mapOf("message" to "Hello Spring")
        //Map-> Jackson ->JSON -> HTTP Response
        //You didn’t write: JSONObject(...) toJson(...) serialize(...)
        // Kotlin Object → JSON using Jackson.*/

        // lets return a greeting object
        val greet = Greeting("Hello, world!",
            "Akycfc")
        println(greet.message)
        return greet


   }
}