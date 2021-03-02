package com.yue.springsecurityjwtlogindemo2.utils;

import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/25 22:21
 */
@Component
public class JWTTokenUtils {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ACCOUNT_ID = "accountId";
    private static final String CLAIM_KEY_USER_AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:28
     * @Description 根据用户信息生成Token。
     * @Param
     * @Return
     **/
    public String generateToken(SystemLoginAccount userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ACCOUNT_ID,userDetails.getAccountId());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Set<String> authoritiesSet = AuthorityUtils.authorityListToSet(authorities);
        List<String> authoritiesList = new ArrayList<>();
        for (String stringAuthority: authoritiesSet){
            authoritiesList.add(stringAuthority);
        }

        System.out.println("generatorToken: "+authoritiesList);
        claims.put(CLAIM_KEY_USER_AUTHORITIES,authoritiesList);
        return generateToken(claims);
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:31
     * @Description 根据传入荷载生成token。
     * @Param
     * @Return
     **/
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(convertLongToDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:36
     * @Description 根据token获取到用户名。
     * @Param
     * @Return
     **/
    public String getUserNameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/28 15:35
     * @Description 根据Token获得用户id
     * @Param
     * @Return
     **/
    public Integer getAccountIdFromToken(String token){
        Integer accountId;
        try {
            Claims claims = getClaimsFromToken(token);
            accountId = Integer.parseInt(claims.get(CLAIM_KEY_ACCOUNT_ID).toString());
        }catch (Exception e) {
            accountId = null;
        }
        return accountId;
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/28 15:35
     * @Description 根据Token获得用户id
     * @Param
     * @Return
     **/
    public List<String> getAuthoritiesFromToken(String token){
        List<String> resultAuthorities;
        try {
            Claims claims = getClaimsFromToken(token);
            Object authorityObject = claims.get(CLAIM_KEY_USER_AUTHORITIES);
            resultAuthorities = (List<String>) authorityObject;
        }catch (Exception e) {
            resultAuthorities = null;
        }
        return resultAuthorities;
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:38
     * @Description 取到Token的荷载
     * @Param
     * @Return
     **/
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:42
     * @Description 根据用户名和Token,判断Token是否有效。
     * @Param
     * @Return
     **/
    public boolean validateToken(String token, SystemLoginAccount userDetails) {
        String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())
            && isTokenExpired(token));
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:44
     * @Description 判断Token是否过期
     * @Param
     * @Return false:expired;true:effective.
     **/
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return !expiredDate.before(new Date());
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:47
     * @Description 从Token中获取到失效时间。
     * @Param
     * @Return
     **/
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:51
     * @Description 判断Token是否需要刷新。
     * @Param
     * @Return
     **/
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/25 22:33
     * @Description 将long类型时间转换为Date类型（Token失效时间）。
     * @Param
     * @Return
     **/
    private Date convertLongToDate(){
        return new Date(System.currentTimeMillis()+expiration);
    }

}
