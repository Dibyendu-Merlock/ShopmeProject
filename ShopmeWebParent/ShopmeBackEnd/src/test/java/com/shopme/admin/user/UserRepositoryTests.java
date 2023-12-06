package com.shopme.admin.user;



import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests
{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateNewuserWithOneRole()
    {
        Role roleAdmin = testEntityManager.find(Role.class,1);
        User userRana = new User("RiffRana@gmail.com", "rana2023", "Riff", "Rana");
        userRana.addRole(roleAdmin);

        User savedUser = userRepository.save(userRana);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewuserWithTwoRoles()
    {
        User userDibyendu = new User("dibyenduuchiha007@gmail.com", "Merlock@1234", "Dibyendu", "Mondal");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userDibyendu.addRole(roleEditor);
        userDibyendu.addRole(roleAssistant);

        User savedUser = userRepository.save(userDibyendu);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers()
    {
       Iterable<User> listusers = userRepository.findAll();
        for (User user : listusers) {
            System.out.println(user);
        }

        // lambda expression of this loop will be: -
       // listusers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById()
    {
        User userRana = userRepository.findById(1).get();
        System.out.println(userRana);
        assertThat(userRana).isNotNull();
    }

    @Test
    public void testUpdateUserDetails()
    {
        User userRana = userRepository.findById(1).get();
        userRana.setEnabled(true);
        userRana.setEmail("rana@outook.com");
        userRepository.save(userRana);
    }

    @Test
    public void testUpdateUserRoles()
    {
        User userDibyendu = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userDibyendu.getRoles().remove(roleEditor);
        userDibyendu.addRole(roleSalesperson);

        userRepository.save(userDibyendu);

    }
    @Test
    public void testDeleteUser()
    {
        Integer userId = 2;
        userRepository.deleteById(userId);
        
    }


}

