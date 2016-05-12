package com.mycompany.myapp.DatabaseStuff;

import android.util.Log;

import com.mycompany.myapp.Objects.ExactQuest;
import com.mycompany.myapp.Objects.Landmark;
import com.mycompany.myapp.Objects.Quest;
import com.mycompany.myapp.Objects.Quiz;
import com.mycompany.myapp.Objects.User;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is used to initialise the application's database on first startup.
 * All standard landmarks and quests that are hardcoded here get created and then
 * added in there, together with the standard (initial) user that they belong to.
 *
 * Created by Ruben on 18-03-2016.
 */
public class Initializer {

    private ArrayList<Landmark> landmarks = new ArrayList<>(); //the list of standard landmarks
    private ArrayList<Quest> quests = new ArrayList<>(); //the list of standard quests

    private UUID uuid; //a universally unique identifier, which is randomly generated for every created landmark, quest, and user

    /* Creates all standard landmarks and quests and returns the landmarks. */
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
        // SOMETHING GOES WRONG HERE: WHEN WE ADD THE QUIZ TO THE LANDMARK, THE QUESTS THAT CONTAIN IT ARE NOT DISPLAYED
        // Quiz quiz = new Quiz("Question");
        // quiz.setAnswers(new String[]{"A", "B", "C"});
        // quiz.setRightAnswer("B");
        // korenbeurs.setQuiz(quiz);
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
        //TODO Add information

        //number 11
        Landmark zernike = new Landmark("Zernike Campus", UUID.randomUUID().toString());
        zernike.setLocation(53.238919, 6.534970);
        zernike.setInformation("blank");
        landmarks.add(zernike);

        //number 12
        Landmark vvv = new Landmark("VVV Stad Groningen", UUID.randomUUID().toString());
        vvv.setLocation(53.218954, 6.568045);
        vvv.setInformation("blank");
        landmarks.add(vvv);

        //number 13
        Landmark euroborg = new Landmark("Euroborg Horeca", UUID.randomUUID().toString());
        euroborg.setLocation(53.206883, 6.591976);
        euroborg.setInformation("blank");
        landmarks.add(euroborg);

        //number 14
        Landmark martinikerk = new Landmark("Martinikerk", UUID.randomUUID().toString());
        martinikerk.setLocation(53.219691, 6.568775);
        martinikerk.setInformation("blank");
        landmarks.add(martinikerk);

        //number 15
        Landmark noorderplantsoen = new Landmark("Noorderplantsoen", UUID.randomUUID().toString());
        noorderplantsoen.setLocation(53.223103, 6.555212);
        noorderplantsoen.setInformation("blank");
        landmarks.add(noorderplantsoen);

        //number 16
        Landmark groteMarkt = new Landmark("Grote Markt", UUID.randomUUID().toString());
        groteMarkt.setLocation(53.218999, 6.567643);
        groteMarkt.setInformation("blank");
        landmarks.add(groteMarkt);

        //number 17
        Landmark vismarkt = new Landmark("Vismarkt", UUID.randomUUID().toString());
        vismarkt.setLocation(53.217337, 6.565054);
        vismarkt.setInformation("blank");
        landmarks.add(vismarkt);

        //number 18
        Landmark scheepvaartmuseum = new Landmark("Noordelijk Scheepvaartmuseum", UUID.randomUUID().toString());
        scheepvaartmuseum.setLocation(53.216598, 6.560135);
        scheepvaartmuseum.setInformation("blank");
        landmarks.add(scheepvaartmuseum);

        //number 19
        Landmark prinsenhofGardens = new Landmark("Prinsenhof Gardens", UUID.randomUUID().toString());
        prinsenhofGardens.setLocation(53.221295, 6.569049);
        prinsenhofGardens.setInformation("blank");
        landmarks.add(prinsenhofGardens);

        //number 20
        Landmark universityMuseum = new Landmark("Groningen University Museum", UUID.randomUUID().toString());
        universityMuseum.setLocation(53.218526, 6.562936);
        universityMuseum.setInformation("blank");
        landmarks.add(universityMuseum);

        //number 21
        Landmark bovenkamer = new Landmark("De Bovenkamer van Groningen", UUID.randomUUID().toString());
        bovenkamer.setLocation(53.225684, 6.560090);
        bovenkamer.setInformation("blank");
        landmarks.add(bovenkamer);


        //create all standard quests, TODO: we could also move this to createAllStandardQuests() and get from landmark list (however hardcoded number)

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

        //some extra empty quests to look cool in the list
        ExactQuest pubs = new ExactQuest(UUID.randomUUID().toString(), "Pub Quest", false);
        quests.add(pubs);
        ExactQuest restaurants = new ExactQuest(UUID.randomUUID().toString(), "Restaurant Quest", false);
        quests.add(restaurants);
        ExactQuest nature = new ExactQuest(UUID.randomUUID().toString(), "Nature Quest", false);
        quests.add(nature);

        return landmarks;
    }

    /* Returns all standard quests. */
    public ArrayList<Quest> createStandardQuests(){
        return quests;
    }

    /* Creates the standard user and returns it. */
    public User createStandardUser() { return new User(UUID.randomUUID().toString()); }
}


