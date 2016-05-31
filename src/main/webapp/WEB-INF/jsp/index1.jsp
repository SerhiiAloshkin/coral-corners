<%--
  Created by IntelliJ IDEA.
  User: Infected
  Date: 24.10.2015
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<html>
  <head>
    <title></title>
    <link href="css/corners.css" rel="stylesheet" media="all"/>
    <script type="text/javascript">
      function handleEvent(hIndex, vIndex) {
        //Creating a new XMLHttpRequest object
        var xmlhttp;
        var params = "hIndex=" + hIndex + "&vIndex=" + vIndex;
        if (window.XMLHttpRequest){
          xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
        } else {
          xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
        }
        //Create a asynchronous GET request
        xmlhttp.open("POST", "/home", false);

        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.setRequestHeader("Content-length", params .length);
        xmlhttp.setRequestHeader("Connection", "close");

        xmlhttp.send(params);

        window.location.reload();
      }
    </script>
  </head>
  <body>
    <img src="img/Desc.jpg"/>
    <c:forEach items="${cells}" var="cell">
      <c:if test="${!cell.value.isEmpty()}">
        <img class="${cell.value.getChip().getChipType()}"
             style="top: ${cell.value.getTop()}px; left: ${cell.value.getLeft()}px;"
             src="img/${cell.value.getChip().getChipType()}.png"
             onclick="handleEvent(${cell.value.getCoordinates().getHIndex()}, ${cell.value.getCoordinates().getVIndex()})"
        />
      </c:if>
    </c:forEach>
    <br/>
    <label>Black Value: </label><c:out value="${blackValue}"/>
    <br/>
    <label>White Value: </label><c:out value="${whiteValue}"/>
  </body>
</html>
