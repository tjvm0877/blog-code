package com.its.simple_chatting_app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

	// WebSocketSession은 클라이언트-서버 간 웹소켓 연결을 추적·관리하는 세션 객체이다.
	// 여러 세션을 관리함으로써 실시간 채팅, 브로드캐스트 등 다양한 실시간 기능 구현이 가능
	private final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

	// 웹소켓 연결 성립 시 호출
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 세션을 저장
		SESSIONS.put(session.getId(), session);
	}

	// 텍스트 메시지 수신 시 호출
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메시지 발신자를 제외한 Session에 수신 메시지 Broadcast
		for (WebSocketSession session1 : SESSIONS.values()) {
			if (!session1.getId().equals(session.getId())) {
				session1.sendMessage(message);
			}
		}
	}

	// 웹소켓 연결 종료 시 호출
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 저장된 세션을 삭제
		SESSIONS.remove(session.getId());
	}
}
