package kz.playfootball.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kz.playfootball.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {


    @Override
    public void setSessionAttribute(String attributeName, Object attributeValue) {
        HttpSession session = getSession();
        session.setAttribute(attributeName, attributeValue);
    }

    @Override
    public Object getSessionAttribute(String attributeName) {
        HttpSession session = getSession();
        return session.getAttribute(attributeName);
    }

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        return request.getSession(true);
    }
}
