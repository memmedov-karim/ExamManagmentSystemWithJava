package com.example.ExamManagmentSystem.service.auth.userauth;

import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.exceptions.NotFoundException;
import com.example.ExamManagmentSystem.exceptions.PasswordNotCorrectException;
import com.example.ExamManagmentSystem.repository.UserRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.validator.DtoValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserRepository userRepository;
    private final JsonWebTokenService jsonWebTokenService;
    private final DtoValidator dtoValidator;
    @Override
    public String loginUser(UserLoginDto userLoginDto, HttpServletResponse response){
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();
        dtoValidator.validateDto(userLoginDto);
        boolean isexsistUser = userRepository.existsUserByEmail(email);
        if(!isexsistUser){
            throw new NotFoundException("There is not user with this email:"+email);
        }
        User exsistingUser = userRepository.findByEmail(email);
        String userCorrectPassword = exsistingUser.getPassword();
        if(!password.equals(userCorrectPassword)){
            throw new PasswordNotCorrectException("password is not correct");
        }
        jsonWebTokenService.sendTokenWithCookie(exsistingUser.getId(), "tokenU",response);
        return "Succes login";
    }
    @Override
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        return(jsonWebTokenService.clearCookie(request,response,"tokenU"));
    }
}
