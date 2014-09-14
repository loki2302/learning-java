package me.loki2302;

@Controller
@RequestMapping("/api")
public class MyController1 {
    @RequestMapping("/myMethod")
    public void myMethod(
            @Path("num") int numParameter,
            @Query("str") String stringParameter) {
    }
}
