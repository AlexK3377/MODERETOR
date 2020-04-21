package ua.kiev.prog;


import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Collectors;

@XmlRootElement(name = "xml")
@Component
public class Xml {
    @XmlElement(name = "user")
    private final List<CustomUser> list = new ArrayList<>();

    public Xml(){}

    public List<CustomUser> getList() {
        return list;
    }
    public CustomUser findUserByLogin(String login){
        for (CustomUser user : list) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
    public boolean addUser(String login, String passHash, UserRole role, String email, String phone){
        list.add(new CustomUser(login,passHash,role,email,phone));
        return findUserByLogin(login) != null;
    }
    public void updateUser(String login, String email, String phone){
        CustomUser user = findUserByLogin(login);
        user.setEmail(email);
        user.setPhone(phone);
    }
    public void deleteUsers(List<String> loginList){
        List<CustomUser> toDelete = new ArrayList<>();
        for (CustomUser user : list){
            for (String login : loginList)
                if (user.getLogin().equals(login)) {
                    toDelete.add(user);
                    break;
                }
        }
        list.removeAll(toDelete);
    }
    public List<CustomUser> getListWithoutAdmin(){
        return getList().stream()
                .filter(i -> !i.getRole().toString().equals("ROLE_ADMIN"))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        list.removeIf(customUser -> id.equals(customUser.getId()));
    }

    public boolean existsByLogin(String login) {
//        for (CustomUser customUser : list) {
//            if (login.equals(customUser.getLogin()) {
//                return true;
//            }
//        }
//        return false;
        return list.stream().anyMatch(customUser -> login.equals(customUser.getLogin()));
    }

    public Optional<CustomUser> findUserById(Long id) {
        return list.stream().filter(customUser -> id.equals(customUser.getId())).findFirst();
    }
}