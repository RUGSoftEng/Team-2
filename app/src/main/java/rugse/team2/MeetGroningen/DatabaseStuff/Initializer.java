package rugse.team2.MeetGroningen.DatabaseStuff;

import android.util.Log;

import rugse.team2.MeetGroningen.Objects.ExactQuest;
import rugse.team2.MeetGroningen.Objects.Landmark;
import rugse.team2.MeetGroningen.Objects.Quest;
import rugse.team2.MeetGroningen.Objects.User;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is used to initialise the application's database on first start-up.
 * All standard landmarks and quests that are hardcoded here get created and then
 * added in there, together with the standard (initial) user that they belong to.
 *
 * Created by Ruben on 18-03-2016.
 */
public class Initializer {
    /** The list of standard landmarks. */
    private ArrayList<Landmark> landmarks = new ArrayList<>();
    /** The list of standard quests. */
    private ArrayList<Quest> quests = new ArrayList<>();

    /** A universally unique identifier, which is randomly generated for every created landmark, quest, and user. */
    private UUID uuid;

    /**
     * Creates all standard landmarks and quests and gets the landmarks.
     *
     * @return An ArrayList object containing all hardcoded landmarks.
     */
    public ArrayList<Landmark> createStandardLandmarks(){


        Log.d("UUID", UUID.randomUUID().toString());
        //number ID 1
        Landmark martinitoren = new Landmark("Martinitoren", UUID.randomUUID().toString());
        martinitoren.setLocation(53.219383, 6.568125);
        martinitoren.setInformation("The Martinitoren is the highest church steeple in the city of Groningen and the bell tower of the Martinikerk. It contains a brick spiral staircase consisting of 260 steps, and the carillon within the tower contains 62 bells. The tower is considered one of the main tourist attractions of Groningen and offers a view over the city and surrounding area. The front of the tower shows three pictures above the entrance: the blind poet Bernlef, Saint Martinus, and Rudolf Agricola. All three men are linked to the history of Groningen.");
        landmarks.add(martinitoren);

        //number 2
        Landmark cityHall = new Landmark("City Hall", UUID.randomUUID().toString());
        cityHall.setLocation(53.218586, 6.567310);
        cityHall.setInformation("The Groningen City Hall is the seat of government in Groningen. The city council meets in a modern room downstairs, but upstairs in the former raadszaal (boardroom) the 'Gulden Boek' (golden book) is kept that lists the honored citizens of the town.");
        landmarks.add(cityHall);

        //number 3
        Landmark korenbeurs = new Landmark("Korenbeurs", UUID.randomUUID().toString());
        String[] possibleAnswers = new String[]{"answer A", "B", "C"};
        korenbeurs.setQuiz("General question", "answer A", possibleAnswers);
        korenbeurs.setLocation(53.216863, 6.563781);
        korenbeurs.setInformation("The Korenbeurs (Grain Exchange) is a neoclassical building in Groningen in the Netherlands. It was originally used as an exchange for food grain trade, but now hosts a supermarket.");
        landmarks.add(korenbeurs);

        //number 4
        Landmark synagogue = new Landmark("Synagogue", UUID.randomUUID().toString());
        synagogue.setLocation(53.214776, 6.565342);
        synagogue.setInformation("Groningen's synagogue is one of the few working synagogues left in the country. Sporting Moorish adornments, the century-old structure now houses a school and a temporary exhibition space; its beautifully restored wooden ceiling is one of the interior's highlights.");
        landmarks.add(synagogue);

        //number 5
        Landmark groningerMuseum = new Landmark("Groninger Museum", UUID.randomUUID().toString());
        groningerMuseum.setLocation(53.212292, 6.565761);
        groningerMuseum.setInformation("Occupying three islands in the ring canal in front of the station, the museum is, at the very least, a striking structure that will draw an opinion from any observer. Within is a scintillating mix of international artworks from through the ages.");
        landmarks.add(groningerMuseum);

        //number 6
        Landmark station = new Landmark("Central Station", UUID.randomUUID().toString());
        station.setLocation(53.210951, 6.564069);
        station.setInformation("The station building that still stands today was completed in 1896. Especially the entrance hall is an interesting visit and combines Neo-Gothic and Neo-Renaissance elements.");
        landmarks.add(station);

        //number 7
        Landmark peerd = new Landmark("Peerd van Ome Loeks", UUID.randomUUID().toString());
        peerd.setLocation(53.211328, 6.564065);
        peerd.setInformation("Every Groninger knows the song about 'Het Peerd van Ome Loeks', telling the story that Ome Loeks's horse is dead. The white statue, depicting a horse and its owner, was created in 1959 by Jan de Baat and is the city’s best-known statue.");
        landmarks.add(peerd);

        //number 8
        Landmark aKerk = new Landmark("Aa-Kerk", UUID.randomUUID().toString());
        aKerk.setLocation(53.216373, 6.561790);
        aKerk.setInformation("The Der Aa-kerk (also: A-kerk) is a church from the Middle Ages in the centre of Groningen. Located in the old harbour area, Aa-kerk was once a seaman's church; it partially dates back to the 15th century.");
        landmarks.add(aKerk);

        //number 9
        Landmark academiegebouw = new Landmark("Academiegebouw", UUID.randomUUID().toString());
        academiegebouw.setLocation(53.219203, 6.563126);
        academiegebouw.setInformation("The Academiegebouw is the main building of the University of Groningen; its richly decorated exterior was completed in 1909.");
        landmarks.add(academiegebouw);

        //number 10
        Landmark goudkantoor = new Landmark("Goudkantoor", UUID.randomUUID().toString());
        goudkantoor.setLocation(53.218521, 6.566373);
        goudkantoor.setInformation("The architecture of this restored historic cafe is amazing. Dating from 1635, the 'Gold Office' features a gold-tinted exterior and graceful interior, complete with striking paintings.");
        landmarks.add(goudkantoor);

        //added after we got email from the client

        //number 11
        Landmark zernike = new Landmark("Zernike Campus", UUID.randomUUID().toString());
        zernike.setLocation(53.238919, 6.534970);
        zernike.setInformation("The Zernike Campus is an area in the north of the city that hosts the University of Groningen's Faculty of Mathematics and Natural Sciences, Faculty of Economics and Business, and Faculty of Spatial Sciences, as well as some buildings belonging to the Hanze University of Applied Sciences. It is named after the University of Groningen's own physicist and nobel laureate Fritz Zernike, who received the renowned prize in 1953 for his invention of the phase contrast microscope.");
        landmarks.add(zernike);

        //number 12
        Landmark vvv = new Landmark("VVV Stad Groningen", UUID.randomUUID().toString());
        vvv.setLocation(53.218954, 6.568045);
        vvv.setInformation("The Vereniging Voor Vreemdelingenverkeer Stad Groningen (literally 'Groningen City Association For Alien Traffic') provides free tourist information to visitors, and includes a gift shop. One can go here to learn more about what the city and the province have to offer to tourists. It is located right in the middle of Groningen's main square: the Grote Markt.");
        landmarks.add(vvv);

        //number 13
        Landmark euroborg = new Landmark("Euroborg", UUID.randomUUID().toString());
        euroborg.setLocation(53.206883, 6.591976);
        euroborg.setInformation("The Euroborg is the stadium of local football club FC Groningen, and has a capacity of 22329 seats. Its interior is painted in the club's colours: white and green. Around the stadium, the Euroborg site houses a casino, a movie theater, a school, a supermarket, and a fitness centre, as well as some cafés and restaurants."); //is the 'é' displayed correctly here?
        landmarks.add(euroborg);

        //number 14
        Landmark martinikerk = new Landmark("Martinikerk", UUID.randomUUID().toString());
        martinikerk.setLocation(53.219691, 6.568775);
        martinikerk.setInformation("The Martinikerk (Martin's Church) is Groningen's oldest church. The church and its associated tower (the Martinitoren) are named after Saint Martin of Tours (316–397), the patron saint of the Bishopric of Utrecht to which Groningen belonged from 1024 to 1528.");
        landmarks.add(martinikerk);

        //number 15
        Landmark noorderplantsoen = new Landmark("Noorderplantsoen", UUID.randomUUID().toString());
        noorderplantsoen.setLocation(53.223103, 6.555212);
        noorderplantsoen.setInformation("The Noorderplantsoen is the smaller of the two urban public parks in Groningen, yet the one located more centrally, situated slightly north of the city centre. Its name is Dutch for 'northern public garden'. Long but narrow, it has been a great place to hang out ever since it opened in 1880.");
        landmarks.add(noorderplantsoen);

        //number 16
        Landmark groteMarkt = new Landmark("Grote Markt", UUID.randomUUID().toString());
        groteMarkt.setLocation(53.218999, 6.567643);
        groteMarkt.setInformation("The Grote Markt (Big Market) is Groningen's central square, and has been so for over a thousand years. It includes other prominent landmarks like the Martinitoren, City Hall, Goudkantoor, and temporarily also the VVV building for tourist information. It was largely destroyed during the Second World War, but has been rebuilt to partly restore its former glory.");
        landmarks.add(groteMarkt);

        //number 17
        Landmark vismarkt = new Landmark("Vismarkt", UUID.randomUUID().toString());
        vismarkt.setLocation(53.217337, 6.565054);
        vismarkt.setInformation("The Vismarkt (Fish Market) is an old square almost adjacent to Groningen's main square, the Grote Markt, and has about the same area size, but is longer and less wide. Here you can find the Korenbeurs and behind it the Der Aa-kerk. As the name implies, markets can still be found here on Tuesdays, Thursday, Fridays, and Saturdays, including the traditional fish stalls.");
        landmarks.add(vismarkt);

        //number 18
        Landmark scheepvaartmuseum = new Landmark("Noordelijk Scheepvaartmuseum", UUID.randomUUID().toString());
        scheepvaartmuseum.setLocation(53.216598, 6.560135);
        scheepvaartmuseum.setInformation("The Noordelijk Scheepvaartmuseum (Northern Maritime Museum) was established in 1932, and has move location twice since. It has a large collection of museum exhibits, which provide information about the history of shipbuilding and ship transport within the province of Groningen.");
        landmarks.add(scheepvaartmuseum);

        //number 19
        Landmark prinsenhofGardens = new Landmark("Prinsenhof Gardens", UUID.randomUUID().toString());
        prinsenhofGardens.setLocation(53.221295, 6.569049);
        prinsenhofGardens.setInformation("The Prinsenhof Gardens (Princes' Court Gardens) are the gardens belonging to the Prinsenhof, a historic building that after many uses through the ages now hosts a grand café and hotel. Located in the north east of the city centre, the gardens, which were originally constructed in 1626 when the Prinsenhof was the local residence of the Dutch princes, provide a small, serene environment with roses, herbs, and hornbeam tunnels. Its sundial is said to be the most beautiful sundial of the Netherlands."); //is the 'é' displayed correctly here?
        landmarks.add(prinsenhofGardens);

        //number 20
        Landmark universityMuseum = new Landmark("Groningen University Museum", UUID.randomUUID().toString());
        universityMuseum.setLocation(53.218526, 6.562936);
        universityMuseum.setInformation("The Groningen University Museum is the University of Groningen's very own museum, whose entry is free of charge for everyone. Since 1934, the University Museum has been a scientific museum in the academic heart of the city. Because of its multifaceted collection, in which nature, culture, and science go together, there is plenty to see. It is located near the Academiegebouw, the university's main building.");
        landmarks.add(universityMuseum);

        //number 21
        Landmark bovenkamer = new Landmark("De Bovenkamer van Groningen", UUID.randomUUID().toString());
        bovenkamer.setLocation(53.225684, 6.560090);
        bovenkamer.setInformation("De Bovenkamer van Groningen (The Upstairs Room of Groningen) is a unique, elevated, atmospheric conference room for business related, celebratory, and creative gatherings. Established in 2014, it is located in the former water tank of Groningen's northern water tower, that was built in 1908 and used to hold one million litres of water. It is situated near the edge of the Noorderplantsoen, an urban public park.");
        landmarks.add(bovenkamer);

        //landmarks for the Zernike Quest, with Quizzes

        //number 22
        Landmark bernoulliborg = new Landmark("Bernoulliborg", UUID.randomUUID().toString());
        bernoulliborg.setLocation(53.240455, 6.536686);
        bernoulliborg.setInformation("The Bernoulliborg (10500 meter square gross floor area), located Zernike is a building of the University of Groningen. It hosts up to 350 employees and 500 students. With dimensions of 33 by 83 meters and a height of 27 meters, the building offers space for the central departments of the Faculty of Mathematics and Natural Sciences and the departments of mathematics, computer science and artificial intelligence.");
        String[] bernoulliborgPossibleAnswers = new String[]{"Computing Science", "Biology", "Mathematics"};
        bernoulliborg.setQuiz("What department is not found in Bernoulliborg?", "Biology", bernoulliborgPossibleAnswers);
        landmarks.add(bernoulliborg);

        //number 23
        Landmark linnaeusborg = new Landmark("Linnaeusborg", UUID.randomUUID().toString());
        linnaeusborg.setLocation(53.241201, 6.538647);
        linnaeusborg.setInformation("‘Linnaeusborg’ is the new building for the Centre for Life Sciences of the Faculty of Mathematics and Natural Sciences of the University of Groningen. The design forms a component of the WEST8 urban development plan, in which Zernike College will be transformed from a technocratic outpost of the university into an Arcadian campus in a leafy setting. The building is situated on the eastern periphery of the Zernike complex, directly adjacent to the moat and the nature area beyond. Housing for animals is situated on the north side, with greenhouses and test beds to the south.");
        String[] linnaeusborgPossibleAnswers = new String[]{"North", "East", "West"};
        linnaeusborg.setQuiz("Which side of the campus is the Linnaeusborg located at?", "East", linnaeusborgPossibleAnswers);
        landmarks.add(linnaeusborg);

        //number 24
        Landmark albertHeijnZernike = new Landmark("Albert Heijn Zernike", UUID.randomUUID().toString());
        albertHeijnZernike.setLocation(53.240411, 6.536090);
        albertHeijnZernike.setInformation("Albert Heijn B.V. is the largest Dutch supermarket chain, founded in 1887 in Oostzaan, Netherlands. It is named after Albert Heijn, Sr., the founder of the first store in Oostzaan.\n" +
                "Albert Heijn is the oldest organization owned and operated by the Dutch supermarket operator Ahold. The founder of the company (Albert Heijn) was born in 1865. In fact, the name \"Ahold\" is short for Albert Heijn Holding. The stores are full service markets (as opposed to discounters) known for their focus on quality stores and products. This focus on superior products is part of the reason that Albert Heijn is regarded as one of the more expensive supermarkets in the Netherlands, Belgium and Curaçao. However, after a recent price war - initiated by Albert Heijn itself and involving nearly all Dutch supermarket chains from late 2003 all the way into 2006 - the prices are now at a more normal level.[1]\n" +
                "At the moment, Albert Heijn's market share is around 30%. The company's logo consists of a stylized \"ah\" beside a white swoosh.");
        String[] AHPossibleAnswers = new String[]{"Belgium", "France", "Austria"};
        albertHeijnZernike.setQuiz("In which one of the following countries is Albert Heijn found?", "Belgium", AHPossibleAnswers);
        landmarks.add(albertHeijnZernike);


        //create all standard quests TODO: we could also move this to createAllStandardQuests() and get from the landmark list there (would have to use hardcoded numbers however)

        //number 1
        ExactQuest essentialQuest = new ExactQuest(UUID.randomUUID().toString(), "Groningen Highlights", false);
        essentialQuest.addLandmark(martinitoren);
        essentialQuest.addLandmark(cityHall);
        essentialQuest.addLandmark(korenbeurs);
        essentialQuest.addLandmark(synagogue);
        essentialQuest.addLandmark(groningerMuseum);
        essentialQuest.addLandmark(station);
        essentialQuest.addLandmark(peerd);
        essentialQuest.addLandmark(aKerk);
        essentialQuest.addLandmark(academiegebouw);
        essentialQuest.addLandmark(goudkantoor);

        quests.add(essentialQuest);

        //number 2
        ExactQuest reversedEssential = new ExactQuest(UUID.randomUUID().toString(), "Small Adventure", false);
        reversedEssential.addLandmark(korenbeurs);
        reversedEssential.addLandmark(cityHall);
        reversedEssential.addLandmark(martinitoren);
        quests.add(reversedEssential);

        //number 3
        ExactQuest zernikeWithQuiz = new ExactQuest(UUID.randomUUID().toString(), "Zernike Tour (with Quiz examples)", false);
        zernikeWithQuiz.addLandmark(linnaeusborg);
        zernikeWithQuiz.addLandmark(bernoulliborg);
        zernikeWithQuiz.addLandmark(albertHeijnZernike);
        quests.add(zernikeWithQuiz);

        //some extra empty quests to look cool in the list
        /*
        ExactQuest pubs = new ExactQuest(UUID.randomUUID().toString(), "Pub Quest", false);
        quests.add(pubs);
        ExactQuest restaurants = new ExactQuest(UUID.randomUUID().toString(), "Restaurant Quest", false);
        quests.add(restaurants);
        ExactQuest nature = new ExactQuest(UUID.randomUUID().toString(), "Nature Quest", false);
        quests.add(nature);
        */
        return landmarks;
    }

    /**
     * Gets all standard quests.
     *
     * @return An ArrayList object containing all hardcoded quests.
     */
    public ArrayList<Quest> createStandardQuests(){
        return quests;
    }

    /**
     * Creates the standard user and gets it.
     *
     * @return The User object corresponding to the hardcoded user.
     */
    public User createStandardUser() {
        return new User(UUID.randomUUID().toString());
    }
}


