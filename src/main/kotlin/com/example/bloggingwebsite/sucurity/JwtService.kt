package com.example.bloggingwebsite.sucurity

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap


@Service
class JwtService {

    @Value("\${spring.security.jwt.expiration}")
    private val jwtExpiration: Long = 0

    @Value("\${spring.security.jwt.refresh-token.expiration}")
    private val refreshExpiration: Long = 0

    @Value("\${spring.security.jwt.secret-key}")
    private val secretKey: String = ""


    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims!!)
    }


    fun generateToken(userDetails: UserDetails): String {
        val map = HashMap<String, Any>()
        userDetails.authorities.forEach { grantedAuthority ->
            map[grantedAuthority.authority] = grantedAuthority.authority
        }
        return generateToken(map, userDetails)
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        val map = HashMap<String, Any>()
        userDetails.authorities.forEach { grantedAuthority ->
            map[grantedAuthority.authority] = grantedAuthority.authority
        }
        return buildToken(map, userDetails, refreshExpiration)
    }


    fun generateToken(
        claims: Map<String, Any>,
        userDetails: UserDetails,
    ): String {
        return buildToken(claims, userDetails, jwtExpiration)
    }


    private fun extractAllClaims(token: String): Claims? {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }


    fun buildToken(
        claims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long,
    ): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey()/*, SignatureAlgorithm.HS256*/)
            .compact();
    }

    fun isValidToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun getSignInKey(): Key {
        val decode = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(decode)
    }


}