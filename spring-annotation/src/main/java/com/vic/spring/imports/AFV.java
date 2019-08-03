package com.vic.spring.imports;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


//@EnableSpringStudy(name = "bb")
@Import(SpringStudySelector.class)
@Configuration
public class AFV {
}
