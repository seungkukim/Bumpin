# CS496_Week2
계획한 여행 경로가 뜨도록 하는 앱입니다. 


------------
#### FrontEnd

 REtrofit 을 이용하여 안드로이드 스튜디오와 서버를 연결하였다. 
 API service 를 이용하여 서버에게 보낸 수 있는 url 과 사용자 함수를  연결해주었다. 
 Google map Api를 사용하여 지도를 띄우도록 하였다.
 
 로그인 시 입력이 비어있거나 유효하지 않은 사용자일 경우. toast 를 띄우도록 하였다. 
 회원가입 시에도 동일하게 동작하도록 하였다. 




------------
#### BackEnd

##### [ Server ]
Django 이용

모델을 3가지로 분류

1.login {userName, passWord}

2.trip {userName, tripName, data, totalLength}

3.friend (userName, userFriend}

서버는 django 내장 sqlite3 사용

urls.py, views.py를 이용하여 url request 처리했다.
