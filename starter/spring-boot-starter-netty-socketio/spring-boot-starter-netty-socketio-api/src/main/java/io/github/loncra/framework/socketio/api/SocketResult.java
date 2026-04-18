package io.github.loncra.framework.socketio.api;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.id.IdEntity;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Socket 推送结果容器。
 * <p>
 * 用于在一次业务流程中暂存待发送的多条 Socket 消息，
 * 最终由拦截器或响应增强器统一发送。
 *
 * @author maurice.chen
 */
public class SocketResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 7253166403465776076L;

    /**
     * 如果存在相同的事件是否替换原有的值:true 是, 否则 false
     */
    private boolean removeIfExist = true;

    /**
     * 待发送消息集合。
     */
    private List<AbstractSocketMessageMetadata<Object>> messages = new LinkedList<>();

    public SocketResult() {
    }

    public SocketResult(List<AbstractSocketMessageMetadata<Object>> messages) {
        this(true, messages);
    }

    public SocketResult(
            boolean removeIfExist,
            List<AbstractSocketMessageMetadata<Object>> messages
    ) {
        this.removeIfExist = removeIfExist;
        this.messages = messages;
    }

    /**
     * 添加 socket 结果集集合
     *
     * @param socketResults socket 结果集集合
     */
    public void addAllSocketResult(Collection<? extends SocketResult> socketResults) {

        if (CollectionUtils.isEmpty(socketResults)) {
            return;
        }

        socketResults.forEach(this::addSocketResult);
    }

    /**
     * 添加消息结果集
     *
     * @param socketResult 消息结果集
     */
    public void addSocketResult(SocketResult socketResult) {

        if (socketResult == null) {
            return;
        }

        messages.forEach(message -> removeIfExist(message, getMessages()));
        messages.addAll(socketResult.getMessages());
    }

    public List<AbstractSocketMessageMetadata<Object>> getMessages() {
        return messages;
    }

    public void setMessages(List<AbstractSocketMessageMetadata<Object>> messages) {
        this.messages = messages;
    }

    /**
     * 删除 existMessageList 集合中，存在 newMessage 事件类型的所有数据
     *
     * @param newMessage       新消息
     * @param existMessageList 已存在的消息集合
     */
    private void removeIfExist(
            AbstractSocketMessageMetadata<Object> newMessage,
            List<AbstractSocketMessageMetadata<Object>> existMessageList
    ) {

        if (!removeIfExist) {
            return;
        }

        List<AbstractSocketMessageMetadata<?>> exitList = existMessageList
                .stream()
                .filter(u -> u.getEvent().equals(newMessage.getEvent()))
                .collect(Collectors.toList());

        List<AbstractSocketMessageMetadata<?>> result = findSameSocketMessage(newMessage, new LinkedList<>(exitList));

        //noinspection SuspiciousMethodCalls
        existMessageList.removeAll(result);
    }


    /**
     * 查询相对事件的 socket 消息
     *
     * @param newMessage       新的数据
     * @param existMessageList 已存在的数据
     *
     * @return 相同事件的 socket 消息集合
     */
    private List<AbstractSocketMessageMetadata<?>> findSameSocketMessage(
            AbstractSocketMessageMetadata<?> newMessage,
            List<AbstractSocketMessageMetadata<?>> existMessageList
    ) {

        Object newData = newMessage.getMessage().getData();
        List<AbstractSocketMessageMetadata<?>> result = new LinkedList<>();

        if (IdEntity.class.isAssignableFrom(newData.getClass())) {
            IdEntity<Object> newIdEntity = CastUtils.cast(newData);
            for (AbstractSocketMessageMetadata<?> message : existMessageList) {

                Object oldData = message.getMessage().getData();

                if (!IdEntity.class.isAssignableFrom(oldData.getClass())) {
                    continue;
                }

                IdEntity<Object> oldIdEntity = CastUtils.cast(oldData);

                if (Objects.equals(oldIdEntity.getId(), newIdEntity.getId())) {
                    result.add(message);
                }
            }
        }

        return result;
    }

}
