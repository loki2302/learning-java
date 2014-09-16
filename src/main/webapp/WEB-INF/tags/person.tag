<%@ tag body-content="empty" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="age" required="true" %>

<div class="person" style="border: 1px solid red;">
    <p>Name is ${name}</p>
    <p>${age} years old</p>
</div>