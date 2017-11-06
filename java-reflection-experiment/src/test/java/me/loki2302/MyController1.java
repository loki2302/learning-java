package me.loki2302;

import me.loki2302.annotations.Controller;
import me.loki2302.annotations.Path;
import me.loki2302.annotations.Query;
import me.loki2302.annotations.RequestMapping;

@Controller
@RequestMapping("/api")
public class MyController1 {
    @RequestMapping("/myMethod")
    public void myMethod(
            @Path("num") int numParameter,
            @Query("str") String stringParameter) {
    }
}
