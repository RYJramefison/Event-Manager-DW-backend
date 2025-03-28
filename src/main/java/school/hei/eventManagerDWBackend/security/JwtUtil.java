//
//
//package school.hei.eventManagerDWBackend.security;
//
//import org.springframework.stereotype.Component;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtUtil {
//
//    private final String secretKey = "yourSuperSecretKey";  // Changez ça en fonction de votre clé secrète
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new java.util.Date());
//    }
//
//    private java.util.Date extractExpiration(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//    }
//}
//
//
