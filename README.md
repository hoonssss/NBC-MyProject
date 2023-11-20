API 명세서
https://documenter.getpostman.com/view/30871154/2s9Ye8futT

1. 처음 설계한 API 명세서에 변경사항이 있었나요?
변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!

비슷한 API 명으로 변경하였습니다.

3. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?

ERD를 보고 개발하였기 떄문에 헷갈리는 부분이 많이 사라졌습니다.


4. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?

쿠키의 취약점이 사라짐, 다양한 설정이 가능함

5. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?

중요 정보는 JWT에 담지 말아야함. 디코딩 시 보안 문제 발생


6. 만약 댓글이 여러개 달려있는 할일을 삭제하려고 한다면 무슨 문제가 발생할까요? Database 테이블 관점에서 해결방법이 무엇일까요?

게시판 글을 삭제할 시 댓글은 게시판과 관계 및 fk가 설정되어 있어서 문제가 발생할 수 있음.

관련 데이터도 DROP하면 될 것 같음. On Delete CasCade설정, 트리거 사용

7. IoC / DI 에 대해 간략하게 설명해 주세요!

IoC(제어의 역전) 소프트웨어 디자인 패턴 중 하나로 프레임워크가 주도하는 제어 흐름을 의미

DI(의존성 주입) IoC의 한 형태로 컴포넌트가 다른 컴포넌트에 의존하는 것을 외부에서 주입하는 디자인 패턴