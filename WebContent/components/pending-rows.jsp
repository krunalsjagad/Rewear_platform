<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%
  List<Item> list = (List<Item>) request.getAttribute("pendingItems");
  if (list != null) {
    for (Item it : list) {
%>
  <tr>
    <td><%= it.getId() %></td>
    <td><%= it.getTitle() %></td>
    <td><%= it.getUserId() %></td>
    <td><%= it.getCreatedAt() %></td>
    <td>
      <form method="post" action="admin" style="display:inline">
        <input type="hidden" name="itemId" value="<%= it.getId() %>"/>
        <button name="action" value="approve">Approve</button>
        <button name="action" value="reject">Reject</button>
      </form>
    </td>
  </tr>
<%
    }
  }
%>
