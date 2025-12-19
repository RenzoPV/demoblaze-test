package petstore;

import com.intuit.karate.junit5.Karate;

public class PetStoreTest {
    
    @Karate.Test
    Karate runPetStoreTests() {
        return Karate.run("petstore").relativeTo(getClass());
    }
}
