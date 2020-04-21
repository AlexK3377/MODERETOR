package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private ParserXml parserXml;

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return parserXml.xmlToClass().getList();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return parserXml.xmlToClass().findUserByLogin(login);
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        Xml xml = parserXml.xmlToClass();
        ids.forEach(id -> {
            Optional<CustomUser> user = xml.findUserById(id);
            user.ifPresent(u -> {
                if ( ! AppConfig.ADMIN.equals(u.getLogin())) {
                    xml.deleteById(u.getId());
                }
            });
        });
        parserXml.parserClassToXml(xml);
    }

    @Transactional
    public void deleteUsersByLogin(List<String> logins) {
        Xml xml = parserXml.xmlToClass();
        logins.forEach(login -> {
            CustomUser user = xml.findUserByLogin(login);
            if (user != null && !AppConfig.ADMIN.equals(user.getLogin())) {
                xml.deleteById(user.getId());
            }
        });
        parserXml.parserClassToXml(xml);
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String email,
                           String phone) {
        Xml xml = parserXml.xmlToClass();
        if (xml.existsByLogin(login))
            return false;
        xml.addUser(login, passHash, role, email, phone);
        parserXml.parserClassToXml(xml);

        return true;
    }

    @Transactional
    public void updateUser(String login, String email, String phone) {
        Xml xml = parserXml.xmlToClass();
        CustomUser user = xml.findUserByLogin(login);
        if (user == null)
            return;

        xml.updateUser(login, email, phone);
        parserXml.parserClassToXml(xml);
    }

    public List<CustomUser> getListWithoutAdmin() {
        return parserXml.xmlToClass().getListWithoutAdmin();
    }
}
