package mindswap.academy.moviereview_api.service.user;

import lombok.AllArgsConstructor;
import mindswap.academy.moviereview_api.command.user.UserDto;
import mindswap.academy.moviereview_api.command.user.UserUpdateDto;
import mindswap.academy.moviereview_api.converter.user.IUserConverter;
import mindswap.academy.moviereview_api.exception.*;
import mindswap.academy.moviereview_api.persistence.model.user.User;
import mindswap.academy.moviereview_api.persistence.model.user.role.Role;
import mindswap.academy.moviereview_api.persistence.repository.user.IUserRepository;
import mindswap.academy.moviereview_api.persistence.repository.user.role.IRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static mindswap.academy.moviereview_api.exception.ExceptionMessages.*;

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
    public UserDto getUser(Long id) {
        User user = this.REPOSITORY.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return this.CONVERTER.converter(user, UserDto.class);
    }

    @Override
    public List<UserDto> search(Long roleId, String firstName, String lastName, String email) {
        if (roleId == null && firstName.equals("") && lastName.equals("") && email.equals(""))
            throw new BadRequestException(AT_LEAST_1_PARAMETER);

        return this.CONVERTER.converterList(
                this.REPOSITORY.search(roleId, firstName, lastName, email), UserDto.class);
    }

    @Override
    public UserDto add(UserDto userDto) {
        this.ROLE_REPOSITORY.findById(userDto.getRoleId())
                .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));

        this.REPOSITORY.findByEmail(userDto.getEmail())
                .ifPresent(user -> {
                    throw new ConflictException(EMAIL_REGISTERED);
                });

        User user = this.CONVERTER.converter(userDto, User.class);
        this.REPOSITORY.save(user);

        return this.CONVERTER.converter(user, UserDto.class);
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
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Role role = user.getRoleId();

        if (userUpdateDto.getRoleId() != null)
            role = this.ROLE_REPOSITORY.findById(userUpdateDto.getRoleId())
                    .orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));

        userUpdateDto.setRoleId(null);
        User updatedUser = this.CONVERTER.converterUpdate(userUpdateDto, user);
        updatedUser.setRoleId(role);
        return this.CONVERTER.converter(
                this.REPOSITORY.save(updatedUser), UserDto.class);
    }
}
