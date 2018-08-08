<?php
session_start();
if(isset($_SESSION['username']) && isset($_SESSION['userid']) && isset($_SESSION['email'])){$reply=array();$reply["error"]=false;$reply["logged"]=true;$reply["username"]=$_SESSION['username'];$reply["email"]=$_SESSION['email'];$reply["id"]=$_SESSION['userid'];echo json_encode($reply);exit();}else{
  if(isset($_GET['enum'],$_GET['password'])){
    class Login{
      private $_EMAIL,$_PASSWORD,$_JSON_responce=array(),$_USER;
      public function __setLOG($_E,$_P){
        $this->_EMAIL=$this->__CLEAN($_E);$this->_PASSWORD=md5($this->__CLEAN($_P));$this->__Login();}
        private function __CLEAN($input){$input=addslashes($input);$input=trim($input);return $input;}
        private function __Login(){require_once("..\includes\connect.php");$_getCount="SELECT count(user_id) from signup where numbers='$this->_EMAIL' AND pass='$this->_PASSWORD'";$sqlCNT=mysqli_query($_CNX->__CONX(),$_getCount);if(!$sqlCNT){echo mysqli_error_no($sqlCNT);exit();}
        else{
          if(mysqli_fetch_row($sqlCNT)[0]==0){$this->_JSON_responce["error"]=true;$this->_JSON_responce["message"]="User Details Not Authentic";}
          else{
            $_getUSER="SELECT username,user_id,numbers from signup WHERE numbers='$this->_EMAIL' AND pass='$this->_PASSWORD'";$sqlGET=mysqli_query($_CNX->__CONX(),$_getUSER);
            if(!$sqlGET){$this->_JSON_responce["error"]=true;$this->_JSON_responce["message"]="Desired Operation Couldn't Be Completed";}
            else{
              $this->_USER=mysqli_fetch_assoc($sqlGET);$this->__SESSIONS($this->_USER['user_id'],$this->_USER['username'],$this->_USER['numbers']);$this->__COOKIES($this->_USER['user_id'],$this->_USER['username'],$this->_USER['numbers']);$this->_JSON_responce["error"]=false;
              $this->_JSON_responce["logged"]=true;
              $this->_JSON_responce["username"]=$this->_USER['username'];$this->_JSON_responce["id"]=$this->_USER['user_id'];$this->_JSON_responce["email"]=$this->_USER['numbers'];}}}echo json_encode($this->_JSON_responce);}
              private function __SESSIONS($identity,$UN,$EM){$_SESSION['username']=$UN;$_SESSION['userid']=$identity;$_SESSION['email']=$EM;}
              private function __COOKIES($identity,$UN,$EM){setcookie("user",$UN,strtotime('+30 days'),"/","","",TRUE);setcookie("id",$identity,strtotime('+30 days'),"/","","",TRUE);setcookie("nums",$EM,strtotime('+30 days'),"/","","",TRUE);}};
  $_LOG=new Login;
  $_LOG->__setLOG($_GET['enum'],$_GET['password']);}
  else{
    $reply=array();
    $reply["error"]=true;$reply["message"]="Input Fields Empty";echo json_encode($reply);}}
?>