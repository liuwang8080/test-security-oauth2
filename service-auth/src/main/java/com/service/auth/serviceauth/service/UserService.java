package com.service.auth.serviceauth.service;



import com.service.auth.serviceauth.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuwang
 * @date 13/07/2022 4:02 PM
 */
@Service
public class UserService implements UserDetailsService {
    private List<User> userList;
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
       // String password = passwordEncoder.encode("123456");
        String password = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("user_1", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("user_2", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("macro", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("mark", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> findUserList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}