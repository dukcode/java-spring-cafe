package com.kakao.cafe.users;

import com.kakao.cafe.exception.CafeRuntimeException;
import com.kakao.cafe.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ModelAndView join(User user, ModelAndView model) {

        try {
            userService.join(user);
        } catch (CafeRuntimeException e) {
            model.getModel().put("user", user);
            model.getModel().put("errorMessage", e.getMessage());
            model.setStatus(HttpStatus.BAD_REQUEST);
            model.setViewName("user/form");
            return model;
        }

        model.setViewName("redirect:/users");
        return model;
    }

    @GetMapping("/users")
    public ModelAndView userList(ModelAndView modelAndView) {
        userService.findUsers()
                .ifPresent(users -> modelAndView.addObject("users", users));
        modelAndView.setViewName("user/list");
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public ModelAndView findProfile(@PathVariable("id") Long id, ModelAndView modelAndView) {

        userService.findOne(id)
                .ifPresentOrElse(user -> {
                    modelAndView.setViewName("user/profile");
                    modelAndView.addObject(user);
                }, () -> {
                    modelAndView.setViewName("user/list");
                    modelAndView.setStatus(HttpStatus.BAD_REQUEST);
                    modelAndView.addObject("errorMessage", "회원이 없습니다.");
                });

        return modelAndView;
    }

}
