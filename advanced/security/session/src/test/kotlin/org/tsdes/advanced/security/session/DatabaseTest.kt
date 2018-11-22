package org.tsdes.advanced.security.session

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.tsdes.advanced.security.session.db.UserRepository
import org.tsdes.advanced.security.session.db.UserService

/**
 * Created by arcuri82 on 08-Nov-17.
 */
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE)
class DatabaseTest {

    @Autowired
    private lateinit var userHandler: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    @BeforeEach
    fun clean(){
        userRepository.deleteAll()
    }

    @Test
    fun testCreateUser(){

        val name = "foo"
        val pwd = "bar"
        val role = "USER"

        val created = userHandler.createUser(name, pwd, setOf(role))

        assertTrue(created)

        val user = userRepository.findById(name).get()

        assertEquals(name, user.username)
        assertNotEquals(pwd, user.password)

        assertTrue(passwordEncoder.matches(pwd, user.password))
    }

}