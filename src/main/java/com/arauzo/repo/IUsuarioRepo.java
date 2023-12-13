package com.arauzo.repo;

import com.arauzo.model.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends ReactiveMongoRepository<Usuario, String>{
		
	Mono<Usuario> findOneByUsuario(String usuario);	
}
