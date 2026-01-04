package com.skimobarber.identity.infrastructure.adapters.out.db;

@Component
public class JpaUserAdapter implements UserRepositoryPort { // Puerto de salida
    private final SpringDataUserRepository repository;

    @Override
    public User save(User user) {
        // Mapear de Dominio a Entidad JPA
        UserEntity entity = UserEntity.fromDomain(user);
        return repository.save(entity).toDomain();
    }
}