<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">document.write('<base href="' + document.location + '" />');</script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="A small app that lets the user export his photos from facebook">
<meta name="author" content="Mouad El Merchichi">

<title>Facebook Photos Exporting WebApp</title>

<!-- App icon -->
<link rel="icon" type="image/png" sizes="32x32" href="src/assets/img/favicon.ico" />

<link rel="stylesheet" type="text/css" href="src/assets/vendor/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="src/assets/vendor/open-iconic/font/css/open-iconic-bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="src/assets/css/common-styles.css" />

<!-- Polyfill(s) for older browsers -->
<script src="node_modules/core-js/client/shim.min.js"></script>
<script src="node_modules/zone.js/dist/zone.js"></script>
<script src="node_modules/systemjs/dist/system.src.js"></script>
<script src="src/systemjs.config.js"></script>
<script type="text/javascript">
   System.import('src/main.js').catch(function(err){ console.error(err); });
</script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
 <![endif]-->
</head>
<body>
   <!-- App Content Start -->
   <main role="main"> 
      <facebook-photos-exporting>
         <div class="loading">
            <div class="loading-bar"></div>
            <div class="loading-bar"></div>
            <div class="loading-bar"></div>
         </div>
      </facebook-photos-exporting> 
   </main>
   <!-- App Content End -->

   <!-- JS Libraries -->
   <script type="text/javascript" src="src/assets/vendor/jquery/jquery-3.2.1.min.js"></script>
   <script type="text/javascript" src="src/assets/vendor/bootstrap/js/popper.min.js"></script>
   <script type="text/javascript" src="src/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>