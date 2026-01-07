
import view.Deck;
import model.Deck;

public class DeckController {
    private DeckDao deckDAO;

    public DeckController() {
        deckDAO = new DeckDao();
    }

    public void createDeck(String front, String back) {
        Deck deck = new Deck(front, back);
        try {
            deckDAO.addDeck(deck);
            System.out.println("Deck saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}