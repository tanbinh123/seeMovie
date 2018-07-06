package com.springBoot.test;

import com.seeMovie.Application;

public @interface SpringBootTest {

	Class<Application> classes();

}
