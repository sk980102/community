package sk.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.community.community.mapper.PublishMapper;
import sk.community.community.mapper.UserMapper;
import sk.community.community.model.Publish;
import sk.community.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static sk.community.community.model.Publish.*;

@Controller
public class PublishController {

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){

        User user = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if(user !=null){
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        if(user ==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Publish publish = new Publish();
        publish.setTitle(title);
        publish.setContent(content);
        publish.setTag(tag);
        publish.setCreator(user.getId());
        publish.setGmtCreate(System.currentTimeMillis());
        publish.setGmtModified(publish.getGmtCreate());
        publishMapper.create(publish);
        return "redirect:/";
    }
}
