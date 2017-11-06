<%@ tag body-content="empty" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="content" required="true" %>

<div style="border: 1px solid red;">
    <div style="border: 1px solid green;">${title}</div>
    <div style="border: 1px solid blue;">${content}</div>
</div>
