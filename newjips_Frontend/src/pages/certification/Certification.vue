<script setup>
import { ref, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import authApi from '@/api/authApi';

const router = useRouter();
const route = useRoute();
const auth = useAuthStore();

// 상태 변수
const isFindingId = ref(route.query.mode === 'id' || !route.query.mode); // 기본값: 아이디 찾기
console.log('id : ', isFindingId.value);
const isFindingPassword = ref(route.query.mode === 'password');
console.log('password : ', isFindingPassword.value);

const find = reactive({
  userId: '',
  name: '',
});

//password 찾는 로직
// const password = async () => {
//   console.log(find);
//   try {
//     const response = await auth.getpassword(find.userId, find.name);
//     console.log('비밀번호 찾기 결과: ', response);
//     // 비밀번호를 보여주는 로직
//     alert(`비밀번호: ${response.password}`);
//   } catch (error) {
//     console.error('비밀번호 찾기 오류: ', error);
//     // userId나 name이 틀린 경우 경고 메시지
//     alert('아이디 또는 이름이 틀렸습니다. 다시 확인해 주세요.');
//   }
// };

const getpassword = async () => {
  console.log(find);
  try {
    const response = await authApi.getpassword(find.userId, find.name);
    console.log('비밀번호 찾기 결과: ', response);
    // 비밀번호를 사용자에게 보여주는 로직
    alert(`비밀번호: ${response}`);
  } catch (error) {
    console.error('비밀번호 찾기 오류: ', error);
    // userId나 name이 틀린 경우 경고 메시지
    alert('아이디 또는 이름이 틀렸습니다. 다시 확인해 주세요.');
  }
};

//mode를 자주 바꿨을 시 뒤로 가기도 계속 해줘야 하기에
//replace로 브라우저 히스토리에 기록을 남기지 않고 '/auth/login'로 바로 가게 해줌.
const selectOption = (option) => {
  if (option === 'id') {
    router.replace({ query: { mode: 'id' } });
    isFindingId.value = true;
    isFindingPassword.value = false;
  } else if (option === 'password') {
    router.replace({ query: { mode: 'password' } });
    isFindingId.value = false;
    isFindingPassword.value = true;
  }
};

// 본인인증 창
const openidentity = ref(true);
const toggleidentity = () => {
  openidentity.value = !openidentity.value;
  openemail.value = false;
};

// 이메일 창
const openemail = ref(false);
const toggleemail = () => {
  openemail.value = !openemail.value;
  openidentity.value = false;
};
</script>

<template>
  <body class="bg-light">
    <div class="container">
      <div class="card">
        <div class="selectoption">
          <button
            :class="['idbutton', { active_borderid: isFindingId }]"
            @click="selectOption('id')"
          >
            아이디 찾기
          </button>
          <button
            :class="['pwbutton', { active_borderpw: isFindingPassword }]"
            @click="selectOption('password')"
          >
            비밀번호 찾기
          </button>
        </div>
        <div v-if="isFindingId">
          <!-- 본인인증 옵션 -->
          <div class="findidentity">
            <button
              :class="['findidentitybutton', { inactive: !openidentity }]"
              type="button"
              @click="toggleidentity"
            >
              <span style="font-weight: bold"> 본인인증으로 찾기 </span>
              <span> {{ openidentity ? '∨' : '∧' }} </span>
            </button>
            <div v-if="openidentity" class="inidentity">
              <div class="identity-phon">
                <img
                  class="mt-2"
                  src="@\assets\images\identity verification.png"
                />
                <p>휴대폰 본인인증</p>
              </div>
              <p class="text">
                *본인명의 휴대폰이 아닌 경우, 인증이 불가합니다.
              </p>
            </div>
          </div>

          <!-- 이메일 옵션 -->
          <form class="findemail" @submit.prevent="getpassword">
            <button
              :class="['findemailbutton', { inactive: !openemail }]"
              type="button"
              @click="toggleemail"
            >
              <span style="font-weight: bold"> 이메일로 찾기</span>
              <span> {{ openemail ? '∨' : '∧' }} </span>
            </button>
            <div v-if="openemail" class="inemail">
              <div class="namewindow">이름</div>
              <div class="input-container">
                <label class="nemelabel" for="name"></label>
                <input
                  class="name-control"
                  type="text"
                  id="name"
                  v-model="find.name"
                  placeholder="이름을 입력하세요"
                />
              </div>
              <div class="emaillabel">이메일</div>
              <div class="input-container">
                <label class="emaillabellabel" for="email"></label>
                <input
                  class="email-control"
                  type="text"
                  id="email"
                  v-model="find.userId"
                  placeholder="이메일을 입력하세요"
                />
              </div>
              <button class="check" type="submit">확인</button>
            </div>
          </form>
        </div>

        <div v-if="isFindingPassword">
          <!-- 본인인증 옵션 -->
          <div class="findidentity">
            <button
              :class="['findidentitybutton', { inactive: !openidentity }]"
              type="button"
              @click="toggleidentity"
            >
              <span style="font-weight: bold"> 본인인증으로 찾기 </span>
              <span> {{ openidentity ? '∨' : '∧' }} </span>
            </button>
            <div v-if="openidentity" class="inidentity">
              <div class="id">아이디</div>
              <div class="input-idcontainer">
                <label class="idlabel" for="id"></label>
                <input
                  class="id-control"
                  type="text"
                  id="id"
                  v-model="find.userId"
                  placeholder="아이디를 입력하세요."
                />
              </div>
              <div class="identity-phon">
                <img
                  class="mt-2"
                  src="@\assets\images\identity verification.png"
                />
                <p>휴대폰 본인인증</p>
              </div>
              <p class="text">
                *본인명의 휴대폰이 아닌 경우, 인증이 불가합니다.
              </p>
            </div>
          </div>

          <!-- 이메일 옵션 -->
          <form class="findemail" @submit.prevent="getpassword">
            <button
              :class="['findemailbutton', { inactive: !openemail }]"
              type="button"
              @click="toggleemail"
            >
              <span style="font-weight: bold"> 이메일로 찾기</span>
              <span> {{ openemail ? '∨' : '∧' }} </span>
            </button>
            <div v-if="openemail" class="inemail">
              <div class="namewindow">이름</div>
              <div class="input-container">
                <label class="nemelabel" for="name"></label>
                <input
                  class="name-control"
                  type="text"
                  id="name"
                  v-model="find.name"
                  placeholder="이름을 입력하세요"
                />
              </div>
              <div class="emaillabel">이메일</div>
              <div class="input-container">
                <label class="emaillabellabel" for="email"></label>
                <input
                  class="email-control"
                  type="text"
                  id="email"
                  v-model="find.userId"
                  placeholder="이메일을 입력하세요"
                />
              </div>
              <button class="check" type="submit">확인</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</template>

<style scope>
.container {
  width: 100%;
  display: flex;
  justify-content: center;
  padding: 5% 0;
}
.card {
  width: 40%;
  max-height: 100%;
  padding: 20px;
  height: 55vh;
}
.selectoption {
  font-size: x-large;
  margin-bottom: 2%;
}

/* 아이디 찾기 버튼 */
.idbutton {
  width: 50%;
  border: 2px solid gray;
  border-radius: 8px 8px 0 0;
  background-color: white;
}
/* 비밀번호 찾기 버튼 */
.pwbutton {
  width: 50%;
  border: 2px solid gray;
  border-radius: 8px 8px 0 0;
  background-color: white;
}
/* 아이디 찾기 활성화시 */
.active_borderid {
  color: #ff8f17;
  border-top: 2px solid #ff8f17;
  border-right: 2px solid #ff8f17;
  border-bottom: 0;
  border-left: 2px solid #ff8f17;
  border-radius: 8px 8px 0 0;
}
/* 비밀번호 찾기 활성화시 */
.active_borderpw {
  color: #ff8f17;
  border-top: 2px solid #ff8f17;
  border-right: 2px solid #ff8f17;
  border-bottom: 0;
  border-left: 2px solid #ff8f17;
  border-radius: 8px 8px 0 0;
}
/* 아이디 찾기 활성화 시 비밀번호 찾기 버튼 변경 */
.active_borderid + .pwbutton {
  border-bottom: 2px solid #ff8f17;
  border-left: 0;
  border-radius: 8px 8px 0 0;
}
/* 비밀번호 찾기 활성화 시 아이디 찾기 버튼 변경 */
/* 인접 형제 선택자: + 선택자는 특정 요소의 바로 다음 형제 요소에만 적용됩니다. */
/* 즉, active_borderpw가 적용된 버튼이 idbutton보다 앞에 있을 때만 적용됩니다. */
/* 그래서 html을 유지 시키고 스타일만 바꾸기 어려웠다.... */
/* :not() 선택자를 사용해 활성 상태가 아닌 버튼을 명시적으로 스타일링해줬다... */
.idbutton:not(.active_borderid) {
  border-right: 0;
  border-bottom: 2px solid #ff8f17;
  border-radius: 8px 8px 0 0;
}

/* 본인인증 옵션 */
.findidentity {
  border: 1px solid gray;
  border-radius: 2px;
}
.findidentitybutton {
  padding: 2% 3%;
  border-style: none;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
.inidentity {
  margin-bottom: 2%;
}
.id {
  padding: 0 2%;
  margin-bottom: 1%;
}
.input-idcontainer {
  padding: 0 2%;
}
.id-control {
  padding: 1%;
  margin-bottom: 2%;
}
.id-control::placeholder {
  font-size: medium;
}
.identity-phon {
  border: 1px solid gray;
  border-radius: 5px;
  width: 20%;
  max-width: 28%;
  display: flex;
  justify-content: center;
  text-align: center;
  margin: 0 3%;
  flex-wrap: wrap;
}
.text {
  margin-top: 2%;
  color: gray;
  margin-left: 2%;
}
/* 이메일 옵션 */
.findemail {
  border: 1px solid gray;
  border-radius: 2px;
  margin-top: 2%;
}
.findemailbutton {
  padding: 2% 3%;
  border-style: none;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
.namewindow {
  margin-top: 3%;
  padding: 0 3%;
}
.input-container {
  margin-top: 2%;
  padding: 0 2%;
}
.name-control {
  padding: 1%;
  margin-top: -1%;
  box-shadow: none;
}
.name-control::placeholder {
  font-size: medium;
}
.emaillabel {
  margin-top: 3%;
  padding: 0 3%;
}
.email-control {
  padding: 1%;
  margin-top: -1%;
  box-shadow: none;
}
.email-control::placeholder {
  font-size: medium;
}
.check {
  margin: 3% 0 3% 2%; /* 상 우 하 좌 */
  padding: 0 5%;
  width: 90%;
  border: 1px solid gray;
  background-color: gainsboro;
}
.inactive {
  background-color: lightgray;
}
/* 작은 화면에서 카드 너비를 80%로 조정 */
@media (max-width: 768px) {
  .card {
    width: 80%;
  }
}

/* 모바일 화면에서 카드 너비를 100%로 조정 */
@media (max-width: 480px) {
  .card {
    width: 100%;
  }
}
</style>
