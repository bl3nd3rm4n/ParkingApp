import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void main() {
        assertEquals(1,1);
    }
    @Test
    public void add(){

        assertEquals(3,1+2);
//        try {
//            String s= new ObjectMapper().writeValueAsString(new User(1,"asd","aaa"));
//            System.out.println(s);
//            User user = new ObjectMapper().readValue(s, User.class);
//            System.out.println(user.getUsername()+user.getPassword());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}