package de.thi.jbsa.prototype.model.cmd;

import java.util.List;
import de.thi.jbsa.prototype.model.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageList {

  private List<Message> messages;
}
