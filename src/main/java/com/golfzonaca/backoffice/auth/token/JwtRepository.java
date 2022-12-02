package com.golfzonaca.backoffice.auth.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class JwtRepository {
    private static final Map<String, String> store = new HashMap<>();

    public Map<String, String> save(String userId, String token) {
        store.put(userId, token);
        return store;
    }

    public Map<String, String> getTokenMap() {
        return store;
    }

    public String getId() {
        Iterator<String> keys = store.keySet().iterator();
        String id = "";
        if (keys.hasNext()) {
            id = keys.next();
        }
        return id;
    }

    public void findAll() {
        Iterator<String> keys = store.keySet().iterator();
        String id = "";
        while (keys.hasNext()) {
            System.out.println("keys.next() = " + keys.next());
        }
    }

    public String getToken(String userId) {
        return store.get(userId);
    }

    public long getUserId() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String token = getToken(getId());
            String claims = JwtHelper.decode(token).getClaims();
            return Long.parseLong(objectMapper.readValue(claims, Map.class).get("id").toString());
        } catch (JsonProcessingException e) {
            throw new NoSuchElementException("유효하지 않은 사용자 정보입니다.");
        }
    }

    public void clearAll() {
        store.clear();
    }
}
