package com.example.SecurityApp.services;

import com.example.SecurityApp.entities.Session;
import com.example.SecurityApp.entities.User;
import com.example.SecurityApp.repo.SessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepo sessionRepo;
    private static final int SESSION_LIMIT = 2; // Should be static final

    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepo.findByUser(user); // Fixed method name

        if (userSessions.size() >= SESSION_LIMIT) { // Ensure limit is strictly enforced
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            sessionRepo.delete(userSessions.get(0)); // Get the least recently used session
        }

        Session newSession = Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();

        sessionRepo.save(newSession);
    }

    public void validateSession(String refreshToken){
        Session session = sessionRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("session not found for this id" +refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepo.save(session);
    }
}
