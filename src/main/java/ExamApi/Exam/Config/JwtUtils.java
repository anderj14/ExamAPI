package ExamApi.Exam.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    private String SECRET_KEY = "secretKey";

//    This method takes a JWT token as input and uses the extractClaim
//    function to get the username (subject) of the JWT token.
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
//    this method uses the extractClaim function
//    to get the expiration date of the JWT token.
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
//    This function is used to extract a specific claim to the token,
//    such as the username.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
//    uses the Jwts library to parse and verify the JWT token, and then
//    returns all the claims contained in the token body.
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
//    This method checks if the expiration date of the JWT
//    token has passed compared to the current time.
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
//    Generates a new JWT token using the information provided by a UserDetails
//    object, which typically contains details of the authenticated user.
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
//    Creates a new JWT token with the provided claims and the given username (subject).
//    Defines the issue date and expiration date for the token.
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt((new Date(System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
//    Validates a JWT token by comparing the username extracted from the token
//    with the username in the provided user details. It also checks if the token
//    has expired.
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
