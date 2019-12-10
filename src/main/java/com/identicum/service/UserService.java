package com.identicum.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.identicum.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void loadUsersFromFile() throws JsonParseException, JsonMappingException, IOException {
        if (this.userRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<User> users = objectMapper.readValue(this.getClass().getClassLoader().getResource("users.json"), new TypeReference<List<User>>(){});
            
            for (User user : users) {
                log.debug("Reading user {}", user.getUsername());
                userRepository.save(user);
            }
        }
	}
}