package com.davydov.DownLoadVSVGO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class StartLoad {

  @Value("${date.load}")
  int n;

  @Autowired
  EmailConfig emailConfig;

  @Autowired
  MailSender mailSender;

  @Autowired
  IDate date;

  @Autowired
  ListRGE listRGEMap;

  @Autowired
  SimpleMailMessage msg;

  @Autowired
  ListEmail listEmail;

  private boolean start = true;

  public void setStart(boolean start) {
    this.start = start;
  }

  public void doStart() throws InterruptedException {

    List<String> listDate = date.getDate();
    Map<String, List<String>> map = listRGEMap.getMap();
    Loader loader = new Loader("xxxx", "xxxx", "xxxx", listDate);
    while (start) {
      loader.setWrite(new StringBuilder());
      loader.setListDate(date.getDate());
      loader.setCount(0);
      for (Map.Entry<String, List<String>> mapRGE : map.entrySet()) {
        loader
            .setLogin(mapRGE.getValue().get(0))
            .setPassword(mapRGE.getValue().get(1))
            .setRge(mapRGE.getKey());
        loader.doAuthorization();
      }
      if (loader.getCount() == map.size() * n) {
        String[] strings = listEmail.getList().toArray(new String[0]);
        StringBuilder write;
        write = loader.getWrite();
        System.out.println(write);
        System.out.println("send mail");
        msg.setFrom(emailConfig.getEmail());
        msg.setTo(strings);
        msg.setSubject("VSVGO");
        msg.setText(String.valueOf(write));
        mailSender.send(msg);
        System.out.println("close mail");
        System.out.println("поток уснул до завтра");
        Thread.sleep(
            24 * 60 * 60 * 1000
                - ((Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 1) * 60 * 60 * 1000
                    - 15 * 60 * 60 * 1000));
      } else {
        System.out.println("поток уснул на 2 мин");
        Thread.sleep(2 * 60 * 1000);
      }
    }
    System.out.println("поток был остановлен");
  }
}
