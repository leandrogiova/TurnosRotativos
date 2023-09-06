package com.neoris.turnosrotativos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.neoris.turnosrotativos.entities.Empleado;

@SpringBootApplication
public class TurnosrotativosApplication {

	static Logger logger = LogManager.getLogger(TurnosrotativosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TurnosrotativosApplication.class, args);
		logger.info("===========================================");
		logger.info("============    APP RUNNING    ============");
		logger.info("===========================================");

		Empleado empleado1 = new Empleado();
		empleado1.setearfechaDeCreacion();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
