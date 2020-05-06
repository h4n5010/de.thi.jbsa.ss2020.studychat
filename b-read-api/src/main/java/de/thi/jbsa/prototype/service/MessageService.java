package de.thi.jbsa.prototype.service;

import de.thi.jbsa.prototype.domain.MessageDoc;
import de.thi.jbsa.prototype.model.event.AbstractEvent;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import de.thi.jbsa.prototype.model.model.Message;
import de.thi.jbsa.prototype.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessageService {

  // TODO Workaround. This does not scale. Create another instance of this service and clients will have different Events
  private final List<AbstractEvent> events = new ArrayList<>(); // workaround

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {this.messageRepository = messageRepository;}

  private Message createMsg(MessagePostedEvent event) {
    Message msg = new Message();
    msg.setCmdUuid(event.getCmdUuid());
    msg.setContent(event.getContent());
    msg.setCreated(new Date());
    msg.setEntityId(event.getEntityId());
    msg.setEventUuid(event.getUuid());
    msg.setSenderUserId(event.getUserId());
    return msg;
  }

  public List<MessageDoc> getAllMessages() {
    return messageRepository.findAll();
  }

  public List<AbstractEvent> getEvents() {
    return events;
  }

  public List<AbstractEvent> getEvents(UUID lastEvent) {
    int indexOfLastEvent = 0;
    if (lastEvent != null) {
      indexOfLastEvent = events.stream()
                               .map(e -> e.getUuid())
                               .collect(Collectors.toList())
                               .indexOf(lastEvent);
    }
    return events
      .stream()
      .skip(indexOfLastEvent + 1)
      .collect(Collectors.toList());

   /*
      .filter(messagePostedEvent -> messagePostedEvent.getUuid().equals(lastEvent))
      .findFirst().map(
        messagePostedEvent -> messagePostedEvents.subList(messagePostedEvents.indexOf(messagePostedEvent) + 1, messagePostedEvents.size()))
      .orElse(messagePostedEvents);

    */
  }

  public void handleMessagePostedEvent(MessagePostedEvent event) {
    MessageDoc doc = new MessageDoc(createMsg(event));
    messageRepository.save(doc);
    events.add(event);

  }
}
