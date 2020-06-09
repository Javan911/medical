<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.gxuwz.medical.domain.role.*" %>
<%@ page import="com.gxuwz.medical.dao.*" %>
<%@ page import="com.gxuwz.medical.domain.vo.*" %>
<%@ page import="com.gxuwz.medical.config.*" %>
<%@ page import="com.gxuwz.medical.domain.menu.*" %>
<%@ page import="com.gxuwz.medical.domain.user.*" %>
<%@ page import="com.gxuwz.medical.domain.area.*" %>
<%@ page import="com.gxuwz.medical.domain.institution.*" %>
<%@ page import="com.gxuwz.medical.domain.disease.*" %>
<%@ page import="com.gxuwz.medical.domain.medical.*" %>
<%@ page import="com.gxuwz.medical.domain.family.*" %>
<%@ page import="com.gxuwz.medical.domain.person.*" %>
<%@ page import="com.gxuwz.medical.domain.payRecord.*" %>
<%@ page import="com.gxuwz.medical.domain.payStandard.*" %>
<%@ page import="com.gxuwz.medical.domain.diseaseCard.*" %>
<%@ page import="com.gxuwz.medical.exception.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
