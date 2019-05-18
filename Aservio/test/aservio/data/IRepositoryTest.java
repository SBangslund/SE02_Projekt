package aservio.data;

import aservio.domain.platform.interfaces.contracts.IRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IRepositoryTest {

    private static IRepository iRepository;

    /**
     * has to be BeforeClass, so that it doesn´t create a connection for every test
     * @throws Exception
     */

    @BeforeAll
    public static void setUp() throws Exception {
        iRepository = new IRepositoryImp();
    }

    @Test
    @Order(1)
    public void addInstitution() {
        assertTrue(iRepository.addInstitution("testInstitution", 999));
    }

    @Test
    @Order(2)
    public void addUser() {
        assertTrue(iRepository.addUser("testUsername", "testPassword", UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229")));
        //assertEquals(false, iRepository.addUser("", "testPassword", UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229")));
    }
    @Order(3)
    @Test
    public void addUserInfo() {
        assertTrue(iRepository.addUserInfo("testEmail@testUser.com", "testFirstname", "testLastname", 123456789, "testPicture", UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229"), 999, "Citizen"));
    }
    @Order(4)
    @Test
    public void addUserAddress() {
        assertTrue(iRepository.addUserAddress("testRoadname", "testCountry", 123456789, "testCity", "testHousenumber", "testLevel", UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229")));
    }
    @Order(5)
    @Test
    public void addActivity() {
        assertTrue(iRepository.addActivity("testActivityName", "testType", new Date(1558193541016L), new Date(1558193541017L), UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744230"), "testDescription"));
    }
    @Order(6)
    @Test
    public void addUserToActivity() {
        assertTrue(iRepository.addUserToActivity(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744230"), UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229")));
    }
    @Order(7)
    @Test
    public void addNote() {
        //assertEquals(true, iRepository.addNote(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744231"), ));
    }
    @Order(8)
    @Test
    public void addNoteToUser() {
    }
    @Order(9)
    @Test
    public void verifyUser() {
        assertEquals("true", iRepository.verifyUser("testUsername", "testPassword"));
        assertEquals("false, wrong username", iRepository.verifyUser("testWrongUsername", "testPassword"));
    }
    @Order(10)
    @Test
    public void getUser() {
        assertEquals("d856148b-beaa-4396-a0fa-aa220d744229", iRepository.getUser("testUsername", "testPassword"));
    }
    @Order(11)
    @Test
    public void getUsersFromActivity() {
        assertEquals("d856148b-beaa-4396-a0fa-aa220d744229", iRepository.getUsersFromActivity(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744230"))[0]);

    }
    @Order(12)
    @Test
    public void getUsersFromInstitution() {
        assertEquals("d856148b-beaa-4396-a0fa-aa220d744229", iRepository.getUsersFromInstitution(999)[0]);
    }
    @Order(13)
    @Test
    public void getActivity() {
        assertArrayEquals(new String[]{"testActivityName", "testType", "1558193541016", "1558193541017", "d856148b-beaa-4396-a0fa-aa220d744230", "testDescription"}, iRepository.getActivity(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744230")));
    }
    @Order(14)
    @Test
    public void getUserRole() {
        assertEquals("Citizen", iRepository.getUserRole("d856148b-beaa-4396-a0fa-aa220d744229") );
    }
    @Order(15)
    @Test
    public void getCitizensFromCaretaker() {

    }
    @Order(16)
    @Test
    public void getUserAddress() {
        assertArrayEquals(new String[]{"testRoadname", "testCountry", "123456789", "testCity", "testHousenumber", "testLevel", "d856148b-beaa-4396-a0fa-aa220d744229"}, iRepository.getUserAddress(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229")));
    }
    @Order(17)
    @Test
    public void getInstitution() {
        assertArrayEquals(new String[]{"testInstitution"}, iRepository.getInstitution(999));
    }
    @Order(18)
    @Test
    public void getUserActivities() {
    }
    @Order(19)
    @Test
    public void getUserInfo() {
    }
    @Order(20)
    @Test
    public void getNotesFromUser() {
    }
    @Order(21)
    @Test
    public void getNote() {
    }
    @Order(22)
    @Test
    public void deleteActivity() {
        assertEquals(true, iRepository.deleteActivity(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744230")));
    }
    @Order(23)
    @Test
    public void deleteNote() {
        //assertEquals(true, iRepository.deleteNote(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744231")));
    }
    @Order(24)
    @Test
    public void deleteUser() {
        assertEquals(true, iRepository.deleteUser(UUID.fromString("d856148b-beaa-4396-a0fa-aa220d744229")));
    }
    @Order(25)
    @Test
    public void deleteInstitution() {
        assertEquals(true, iRepository.deleteInstitution(999));
    }





}