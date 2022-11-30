package com.davydov.DownLoadVSVGO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListEmail implements IListEmail {

  List<String> listEmail = new ArrayList<>();

  @Override
  public List<String> getList() {
    return listEmail;
  }

  @Override
  public void addEmail(String email) {
    listEmail.add(email);
  }
}
