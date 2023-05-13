package uz.jk.model;

public enum UserState {
    START, // ASK FOR CONTACT
    SHARED_CONTACT, //AS FOR LOCATION
    REGISTERED, MAIN_MENU, // SHOW MAIN MENU

    // menus

    CARD_MENU, // SHOW CARD_MENU MENU(crud)
    P2P, //
    PAYMENT,
    HISTORY,

    // card menu
    ADD_CARD,
    MY_CARDS,

    // card types
    HUMO,
    VISA,
    UZ_CARD;

}
