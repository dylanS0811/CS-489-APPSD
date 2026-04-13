package edu.miu.cs.cs489appsd.lab7b.adsgraphqlapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphiqlPageController {

    @GetMapping({"/graphiql", "/graphiql/"})
    public String graphiqlPage() {
        return "forward:/graphiql/index.html";
    }
}
