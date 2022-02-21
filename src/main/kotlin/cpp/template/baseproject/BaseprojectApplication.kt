package cpp.template.baseproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BaseprojectApplication

fun main(args: Array<String>) {
	runApplication<BaseprojectApplication>(*args)
}
