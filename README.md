# Memo(RestApi, Spring)

Dto 구현 MemoResponseDto, MemoRequestDto, Memo <br/>
SpartaController -> @Controller 기능 <br/>
MemoService -> @Service <br/>
MemoRepository -> Map을 이용하여 구분이 어려웠음 <br/>
Repository -> Service -> Controller IoC구현하고 싶었지만 Repository 미구현으로 실패 향후 JPA적용하여 구분짓도록 하겠습니다. <br/>
<br/>
ErerorController -> error.html 보여줌 <br/>
displayMemo -> memo.html 보여줌 <br/>
error.html -> templates을 통해 예외 접근 시 error 웹 표시 https://chb2005.tistory.com/96 참고, 강의 참고하였음 <br/>
memo.html -> gpt를 통하여 기본적인 front틀 구현, 제공함으로써 편의 제공  <br/>
