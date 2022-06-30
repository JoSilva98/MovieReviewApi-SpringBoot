package mindswap.academy.moviereview_api.service.user;

import lombok.AllArgsConstructor;
import mindswap.academy.moviereview_api.command.user.UserDto;
import mindswap.academy.moviereview_api.command.user.UserUpdateDto;
import mindswap.academy.moviereview_api.converter.user.IUserConverter;
import mindswap.academy.moviereview_api.exceptions.EmailAlreadyRegisteredException;
import mindswap.academy.moviereview_api.exceptions.RoleNotFoundException;
import mindswap.academy.moviereview_api.exceptions.UserNotFoundException;
import mindswap.academy.moviereview_api.persistence.model.user.User;
import mindswap.academy.moviereview_api.persistence.repository.user.IUserRepository;
import mindswap.academy.moviereview_api.persistence.repository.user.role.IRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mindswap.academy.moviereview_api.exceptions.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository REPOSITORY;
    private final IUserConverter CONVERTER;
    private final IRoleRepository ROLE_REPOSITORY;

    @Override
    public List<UserDto> getAll() {
        return this.CONVERTER.converterList(
                this.REPOSITORY.findAll(), UserDto.class);
    }

    @Override
    public UserDto add(UserDto userDto) {
        this.ROLE_REPOSITORY.findById(userDto.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND));
        User existingUser = this.REPOSITORY
                .findByEmail(userDto.getEmail())
                .orElse(null);
        if (existingUser != null) throw new EmailAlreadyRegisteredException(EMAIL_REGISTERED);

        User user = this.CONVERTER.converter(userDto, User.class);
        return this.CONVERTER.converter(
                this.REPOSITORY.save(user), UserDto.class);
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        Optional<User> user = this.REPOSITORY.findById(id);
        if (user.isEmpty())
            return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        this.REPOSITORY.deleteById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @Override
    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        User user = this.REPOSITORY.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        User updatedUser = this.CONVERTER.updateDtoToEntity(userUpdateDto, user);
        return this.CONVERTER.converter(
                this.REPOSITORY.save(updatedUser), UserDto.class);
    }
}
