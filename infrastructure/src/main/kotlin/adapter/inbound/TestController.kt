package adapter.inbound

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/hello")
    fun hello(): String {
        println("=== 컨트롤러 호출됨 ===")
        return "Hello World"
    }

    @PostMapping("/post")
    fun testPost(@RequestBody body: Map<String, Any>): ResponseEntity<String> {
        println("=== POST 컨트롤러 호출됨 ===")
        println("Body: $body")
        return ResponseEntity.ok("Success")
    }
}