package com.ossovita.realestateapp.business.concretes;

import com.ossovita.realestateapp.business.abstracts.UserService;
import com.ossovita.realestateapp.core.dataAccess.UserRepository;
import com.ossovita.realestateapp.core.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class UserManager implements UserService {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;
    private UserRepository userRepository;
    Random random;

    public UserManager(RabbitTemplate rabbitTemplate, DirectExchange directExchange, UserRepository userRepository, Random random) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.userRepository = userRepository;
        this.random = random;
    }

    @Override
    public void createDummyUserRequest(int piece) {
        rabbitTemplate.convertAndSend(directExchange.getName(), "userRouting", piece);
    }

    @Override
    public void createDummyUser(int piece) {

        for (int i = 0; i < piece; i++) {
            String userFirstName = getRandomUserFirstName();
            String userLastName = getRandomUserLastName();
            User user = User.builder()
                    .userFirstName(userFirstName)
                    .userLastName(userLastName)
                    .userMail(userFirstName + "." + userLastName + "@mail.com")
                    .build();
            log.info("created user: " + user.toString());
            userRepository.save(user);
        }
    }

    private String getRandomUserFirstName() {
        List<String> userFirstNames = List.of("Hakan", "Ayşe", "Yasin", "Mert", "Bora", "Melike", "Mehmet");
        return userFirstNames.get(random.nextInt(userFirstNames.size()));
    }

    private String getRandomUserLastName() {
        List<String> userLastNames = List.of("Yılmaz", "Çetinok", "Eryigit", "Erpek", "Özyunus", "Yıldız", "Deveci");
        return userLastNames.get(random.nextInt(userLastNames.size()));
    }

    public long getRandomUserPk() {
        return userRepository.getRandomUser().getUserPk();
    }
}
