package com.chinhnd.recruit.event;

import javax.mail.MessagingException;

public interface IMailService {
	void sendRegistrationUserConfirm(String email);
}
