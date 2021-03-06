package com.dmitry.shnurenko.spring.mvc.controllers.register;

import com.dmitry.shnurenko.spring.mvc.dao.user.UserDao;
import com.dmitry.shnurenko.spring.mvc.entity.access.User;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.generator.Generator;
import com.dmitry.shnurenko.spring.mvc.util.sendemail.MailSenderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.AccessException;
import java.util.HashMap;
import java.util.Map;

import static com.dmitry.shnurenko.spring.mvc.controllers.register.RegisterFormNames.*;

/**
 * @@author Dmitry Shnurenko
 */
@Controller
@SessionAttributes("id")
@RequestMapping("/user")
public class RegisterController {

    private final Generator           userIdGenerator;
    private final MailSenderBuilder   googleMailSenderBuilder;
    private final EntityFactory       entityFactory;
    private final UserDao             userDao;
    private final Map<String, String> uniqueIds;
    private final UserLoginManager    userLoginManager;

    @Autowired
    public RegisterController(Generator userIdGenerator,
                              MailSenderBuilder googleMailSenderBuilder,
                              EntityFactory entityFactory,
                              UserDao userDao,
                              UserLoginManager userLoginManager) {
        this.userIdGenerator = userIdGenerator;
        this.googleMailSenderBuilder = googleMailSenderBuilder;
        this.entityFactory = entityFactory;
        this.userDao = userDao;
        this.userLoginManager = userLoginManager;
        this.uniqueIds = new HashMap<>();
    }

    private String login;
    private String email;
    private String password;

    @RequestMapping("/register")
    public String registerUser(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Map<String, String[]> parametersMap = request.getParameterMap();

        login = parametersMap.get(LOGIN.toString())[0];
        email = parametersMap.get(EMAIL.toString())[0];
        password = parametersMap.get(PASSWORD.toString())[0];
        String repeatPassword = parametersMap.get(REPEAT_PASSWORD.toString())[0];

        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("Passwords aren't match...");
        }

        String uniqueId = userIdGenerator.generate();

        uniqueIds.put(login, uniqueId);

        //TODO host and port need change to domain in future
        final String mailContent = "<td><a href=\"http://localhost:8080/user/register/success?id=" + uniqueId + "\">" +
                "http://localhost:8080/register/confirmation</a><br></td>";

        googleMailSenderBuilder.withAddress(email)
                               .withSubject("Register Confirmation")
                               .withHtml(mailContent)
                               .send();

        redirectAttributes.addFlashAttribute("login", login);

        return "redirect:/confirmation";
    }

    @RequestMapping("/register/success")
    public String successRegister(@RequestParam("id") String id, RedirectAttributes attributes) throws AccessException {
        if (!uniqueIds.values()
                      .contains(id)) {
            throw new AccessException("Unique id is not defined... Error authorization...");
        }

        User user = entityFactory.createUser(login, email, password);

        try {
            userDao.save(user);
        } catch (DBException e) {
            e.printStackTrace();
        }

        attributes.addFlashAttribute("access", "true");
        attributes.addFlashAttribute("login", login);

        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();

        String login = parameters.get(LOGIN.toString())[0];
        String password = parameters.get(PASSWORD.toString())[0];

        boolean isUserLogin = false;
        try {
            isUserLogin = userDao.isUserLogin(login, password);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (isUserLogin) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(10 * 60);//10 min

            String id = userIdGenerator.generate();
            userLoginManager.addId(id);

            session.setAttribute("login", login);
            session.removeAttribute("id");
            session.setAttribute("id", id);
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");

        userLoginManager.removeId(id);

        session.removeAttribute("id");
        session.invalidate();
        return "redirect:/";
    }

}

