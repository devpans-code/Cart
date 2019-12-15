package com.cartapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounter implements HttpSessionListener {

	public static List<HttpSession> sessions = new ArrayList<HttpSession>();

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		sessions.add(sessionEvent.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		sessions.remove(sessionEvent.getSession());
	}

	public static int getSessionCount() {
		int logged_in_users = 0;
		for (HttpSession session : sessions)
			if (session.getAttribute("user") != null)
				logged_in_users++;
		return logged_in_users;
	}
}
