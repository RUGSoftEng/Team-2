package rugse.team2.MeetGroningen.Objects;

/**
 * This class represents an exact quest, which is a kind of quest in which its
 * landmarks have to be visited in order, resulting in a more or less fixed route.
 *
 * Created by Ruben on 28-02-2016.
 */
public class ExactQuest extends Quest {
    //private int currentLandmark = 0; //probably not needed

    /**
     * Constructor which calls the superclass's constructor to initialise the quest.
     *
     * @param id The quest's ID.
     * @param name The quest's name.
     * @param isUserGenerated The quest's user-generated flag, which is True for custom quests and False for standard quests.
     */
    public ExactQuest(String id, String name, boolean isUserGenerated) {
        super(id, name, isUserGenerated);
    }

    /** Returns that for this subclass of Quest, landmarks have to be visited in order. */
    public boolean isInOrder(){
        return true;
    }

/* BELOW probably not needed
    public void solvedLandmark(){
        this.currentLandmark++;
    }

    public Landmark getLandmark(int i){
        return this.landmarks.get(i);
    }

    public Landmark getCurrentLandmark(){
        return this.landmarks.get(this.currentLandmark);
    }
*/
}
