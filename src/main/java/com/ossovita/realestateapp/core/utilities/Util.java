package com.ossovita.realestateapp.core.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Util {

    @Bean
    Random getRandom() {
        return new Random();
    }

}

