package io.expensetracker.ExpenseTracker.restApi.dao;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.expensetracker.ExpenseTracker.restApi.EntityMapper.ModelMapperConfig;
import io.expensetracker.ExpenseTracker.restApi.dto.UserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.repo.UserRepository;

@Repository
public class UserDao {
	@Autowired
	UserRepository repo;

	public Users saveUser(Users user) {
		return repo.save(user);
	}

	public Users findUserById(int id) {
		Optional<Users> opuser = repo.findById(id);

        return opuser.orElse(null);
    }

	public Users findUserByEmail(String email) {
		Optional<Users> opuser = repo.findByEmail(email);
        return opuser.orElse(null);
    }

	public UserDto deleteUserById(int id) {
		Optional<Users> u = repo.findById(id);
		if (u.isPresent()) {
			UserDto d = ModelMapperConfig.getModelMapper().map(u, UserDto.class);
			repo.deleteById(id);
			return d;
		}
		return null;
	}

	public List<UserDto> findAllUsers() {
		ModelMapper m = ModelMapperConfig.getModelMapper();
		List<Users> ul = repo.findAll();
		List<UserDto> udl = ul.stream().map(u -> m.map(u, UserDto.class)).toList();
		return udl;
	}
	public boolean doesUserExist(String email){
		return repo.existsByEmail(email);
	}

}
