package io.expensetracker.ExpenseTracker.restApi.dao;

import io.expensetracker.ExpenseTracker.restApi.EntityMapper.ModelMapperConfig;
import io.expensetracker.ExpenseTracker.restApi.dto.MinimalUserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.UserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
	@Autowired
	UserRepository repo;
	@Autowired
	ModelMapper modelMapper;


	@CachePut(value = "usersById" , key = "#user.userId")
	public Users saveUser(UserDto user) {

		Users u = modelMapper.map(user,Users.class);
		u.setPassword(repo.findById(u.getUserId()).get().getPassword());
		return repo.save(u);
	}


	@CacheEvict(value = "usersById" ,key = "#user.userId")
	public Users registerUser(Users user){
		return repo.save(user);
	}

	@Cacheable(value = "usersById" , key = "#userId")
	public Users findUserById(int userId) {
		Optional<Users> opuser = repo.findById(userId);

        return opuser.orElse(null);
    }


	public Users findUserByEmail(String email) {
		Optional<Users> opuser = repo.findByEmail(email);
        return opuser.orElse(null);
    }


	@CacheEvict(value = "usersById" ,key = "#userId")
	public MinimalUserDto deleteUserById(int userId) {
		Optional<Users> u = repo.findById(userId);
		if (u.isPresent()) {
			MinimalUserDto d = ModelMapperConfig.getModelMapper().map(u, MinimalUserDto.class);
			repo.deleteById(userId);
			return d;
		}
		return null;
	}

	public List<MinimalUserDto> findAllUsers() {
		ModelMapper m = ModelMapperConfig.getModelMapper();
		List<Users> ul = repo.findAll();
		List<MinimalUserDto> udl = ul.stream().map(u -> m.map(u, MinimalUserDto.class)).toList();
		return udl;
	}
	public boolean doesUserExist(String email){
		return repo.existsByEmail(email);
	}

}
