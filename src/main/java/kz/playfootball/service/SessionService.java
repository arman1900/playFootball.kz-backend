package kz.playfootball.service;

public interface SessionService {
    void setSessionAttribute(String attributeName, Object attributeValue);

    Object getSessionAttribute(String attributeName);
}
