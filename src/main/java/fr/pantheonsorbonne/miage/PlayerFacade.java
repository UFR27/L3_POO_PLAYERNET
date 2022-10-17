package fr.pantheonsorbonne.miage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface PlayerFacade {

    List<Partner> getAvailablePartners();

    void createGame(String gameName);

    Partner waitForPartner(long delay, TimeUnit unit);

    void joinGame(Partner p);

    void leaveGame(Partner p);

    void send(String str);

    String receive();



}
