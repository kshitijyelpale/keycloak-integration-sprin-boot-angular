package org.example.testflyway.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

class CustomJwt(
    jwt: Jwt?,
    authorities: Collection<GrantedAuthority?>?
) : JwtAuthenticationToken(jwt, authorities) {
    var firstName: String = ""
    var lastName: String = ""
}
