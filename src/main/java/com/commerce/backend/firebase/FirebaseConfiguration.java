package com.commerce.backend.firebase;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.commerce.backend.constants.SystemConstant;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
class FirebaseMessage{
    private FirebaseApp app = null;
    private Object obj = new Object();
	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {
	    GoogleCredentials googleCredentials = GoogleCredentials
	            .fromStream(new ClassPathResource("latifapp-4084e-firebase-adminsdk-crntk-d9d24749f9.json").getInputStream());
	    FirebaseOptions firebaseOptions = FirebaseOptions
	            .builder()
	            .setCredentials(googleCredentials)
	            .build();
	    synchronized(obj) {
	    	if(app == null) {
	     this.app = FirebaseApp.initializeApp(firebaseOptions,  SystemConstant.APP_NAME);
	    	}
	    }
	    return FirebaseMessaging.getInstance(app);
	}
	
	@Bean
	FirebaseAuth firebaseAuth() throws IOException {
	    GoogleCredentials googleCredentials = GoogleCredentials
	            .fromStream(new ClassPathResource("latifapp-4084e-firebase-adminsdk-crntk-d9d24749f9.json").getInputStream());
	    FirebaseOptions firebaseOptions = FirebaseOptions
	            .builder()
	            .setCredentials(googleCredentials)
	            .build();
	    synchronized(obj) {
	    	if(app == null) {
	         this.app = FirebaseApp.initializeApp(firebaseOptions,  SystemConstant.APP_NAME);
	    	}
	    }
	    return FirebaseAuth.getInstance(app);
	}
}