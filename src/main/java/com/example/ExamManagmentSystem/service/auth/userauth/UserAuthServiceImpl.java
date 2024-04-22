package com.example.ExamManagmentSystem.service.auth.userauth;

import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.repository.UserRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
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

    @Override
    public String loginUser(UserLoginDto userLoginDto, HttpServletResponse response){
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();
        if(email==null || email.isEmpty() || password==null || password.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email,password is required");
        }
        boolean isexsistUser = userRepository.existsUserByEmail(email);
        if(!isexsistUser){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this email:"+email);
        }
        User exsistingUser = userRepository.findByEmail(email);
        String userCorrectPassword = exsistingUser.getPassword();
        if(!password.equals(userCorrectPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"password is not correct");
        }
        jsonWebTokenService.sendTokenWithCookie(exsistingUser.getId(), "tokenU",response);
        return "Succes login";
    }
    @Override
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        return(jsonWebTokenService.clearCookie(request,response,"tokenU"));
    }
}
