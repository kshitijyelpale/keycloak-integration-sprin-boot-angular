package org.example.testflyway.jwt

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class CustomJwtConverter : Converter<Jwt, CustomJwt> {

    override fun convert(source: Jwt): CustomJwt {
        val grantedAuthorities = extractGrantedAuthorities(source)
        val customJwt = CustomJwt(source, grantedAuthorities)
        customJwt.firstName = source.getClaimAsString("given_name")
        customJwt.lastName = source.claims["family_name"].toString()
        return customJwt
    }

    private fun extractGrantedAuthorities(jwt: Jwt): Collection<GrantedAuthority> {
        val result = mutableListOf<GrantedAuthority>()
        val realmAccess = jwt.getClaimAsMap("realm_access")
        if (realmAccess != null && realmAccess["roles"] != null) {
            val roles = realmAccess["roles"] as List<*>
            roles.filterIsInstance<String>()
                .filter { it.startsWith("ROLE") }
                .forEach { result.add(SimpleGrantedAuthority(it)) }
        }
        return result
    }
}
