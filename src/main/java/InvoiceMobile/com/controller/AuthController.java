package InvoiceMobile.com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Value("${google.client.id:469979061899-2asbrr7ug7d8cgo9g12bj25nsi7ec0tb.apps.googleusercontent.com}")
    private String googleClientId;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");
        if (idToken == null || idToken.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "idToken is required"));
        }

        try {
            String verifyUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            Map<String, Object> tokenInfo = restTemplate.getForObject(verifyUrl, Map.class);

            if (tokenInfo == null || tokenInfo.containsKey("error_description")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Google Token"));
            }

            // Verify audience matches our Web client ID
            String aud = (String) tokenInfo.get("aud");
            if (aud == null || !aud.equals(googleClientId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Audience mismatch"));
            }

            // Extract user profile information
            String email = (String) tokenInfo.get("email");
            String name = (String) tokenInfo.get("name");
            String picture = (String) tokenInfo.get("picture");
            String googleUserId = (String) tokenInfo.get("sub");

            return ResponseEntity.ok(Map.of(
                "success", true,
                "email", email,
                "name", name,
                "picture", picture,
                "userId", googleUserId
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Verification failed: " + e.getMessage()));
        }
    }
}
