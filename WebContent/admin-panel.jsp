<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>ReWear – Admin Panel</title>
  <link rel="stylesheet" href="css/style.css">
  <script>
    // Poll every 30 seconds
    function refreshPending() {
      fetch('admin?ajax=1')
        .then(res => res.text())
        .then(html => {
          document.getElementById('pending-list').innerHTML = html;
        });
    }
    setInterval(refreshPending, 30000);
    window.onload = refreshPending;
  </script>
</head>
<body>
  <%@ include file="components/navbar.jsp" %>

  <div class="admin-container">
    <h2>Pending Item Approvals</h2>
    <table>
      <thead>
        <tr>
          <th>ID</th><th>Title</th><th>Uploader</th>
          <th>Submitted</th><th>Actions</th>
        </tr>
      </thead>
      <tbody id="pending-list">
        <%-- will be filled by JS via AJAX or initial load --%>
        <%
          request.getRequestDispatcher("components/pending-rows.jsp")
                 .include(request, response);
        %>
      </tbody>
    </table>
  </div>

  <%@ include file="components/footer.jsp" %>
</body>
</html>
