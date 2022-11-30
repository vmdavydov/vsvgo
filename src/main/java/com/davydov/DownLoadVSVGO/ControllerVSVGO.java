package com.davydov.DownLoadVSVGO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ControllerVSVGO {

  @Autowired
  JavaMailSenderImpl javaMailSender;

  @Autowired
  StartLoad startLoad;

  @Autowired
  ListRGE listRGE;

  @Autowired
  ListEmail listEmail;

  @Autowired
  EmailConfig emailConfig;

  @GetMapping("/start")
  public String getStart(Model model) throws Exception {
    System.out.println("email= " + emailConfig.getLogin());
    model.addAttribute(emailConfig);
    startLoad.setStart(true);
    return "start";
  }

  @PostMapping("/start")
  public String saveStart(EmailConfig emailConfig, Model model) {
    this.emailConfig.setLogin(emailConfig.getLogin());
    this.emailConfig.setPassword(emailConfig.getPassword());
    this.emailConfig.setEmail(emailConfig.getEmail());
    javaMailSender.setUsername(this.emailConfig.getEmail());
    javaMailSender.setPassword(this.emailConfig.getPassword());
    model.addAttribute(this.emailConfig);
    return "addEmail";
  }

  @GetMapping("/addEmail")
  public String getAddEmail() {
    for (String st : listEmail.getList()) {
      System.out.println("email for dispatching =" + st);
    }
    return "addEmail";
  }

  @PostMapping("/addEmail")
  public String saveEmail(@ModelAttribute("mail") String emailadd) {
    this.listEmail.addEmail(emailadd);
    for (String st : listEmail.getList()) {
      System.out.println("email for dispatching =" + st);
    }
    return "addEmail";
  }

  @GetMapping("/addRGE")
  public String getAddRGE(Model model) {
    return "addRGE";
  }

  @PostMapping("/addRGE")
  public String saveRGE(
      @ModelAttribute("RGE") String rge,
      @ModelAttribute("login") String login,
      @ModelAttribute("password") String password) {
    listRGE.addRGE(rge, login, password);
    for (Map.Entry<String, List<String>> map : listRGE.getMap().entrySet()) {
      System.out.print(map.getKey() + " ");
    }
    return "addRGE";
  }

  @GetMapping("/startLoad")
  public String startLoad() {
    try {
      startLoad.doStart();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "startLoad";
  }

  @GetMapping("/stop")
  public String getStop() {
    startLoad.setStart(false);
    return "stop";
  }
}
