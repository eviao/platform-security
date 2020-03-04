package xyz.eviao.platform.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlatformSecurityApplication

fun main(args: Array<String>) {
	runApplication<PlatformSecurityApplication>(*args)
}
