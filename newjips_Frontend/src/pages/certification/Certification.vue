<script setup>
import { ref, reactive } from 'vue';

const find = reactive({
  username: '',
  userId: '',
});

//상태 변수
const isFindingId = ref(true);
const isFindingPassword = ref(false);

const selectOption = (option) => {
  if (option === 'id') {
    isFindingId.value = true;
    isFindingPassword.value = false;
  } else if (option === 'password') {
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
          <button @click="selectOption('id')">아이디 찾기</button>
          <button @click="selectOption('password')">비밀번호 찾기</button>
        </div>
        <div v-if="isFindingId">
          <!-- 본인인증 옵션 -->
          <div :class="['findidentity', { inactive: !openidentity }]">
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
          <div :class="['findemail', { inactive: !openemail }]">
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
                  v-model="find.username"
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
              <button class="check" type="sumit">확인</button>
            </div>
          </div>
        </div>

        <div v-if="isFindingPassword">
          <!-- 본인인증 옵션 -->
          <div :class="['findidentity', { inactive: !openidentity }]">
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
          <div :class="['findemail', { inactive: !openemail }]">
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
                  v-model="find.username"
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
          </div>
        </div>
      </div>
    </div>
  </body>
</template>

<style scope>
.container {
  max-width: 100vw;
  display: flex;
  justify-content: center;
  padding: 5% 0;
}
.card {
  width: 35%;
  max-height: 100%;
  padding: 20px;
  height: 55vh;
}
.selectoption {
  display: flex;
  justify-content: center;
  font-size: x-large;
  margin-bottom: 2%;
}
.selectoption button {
  border-style: none;
  background-color: white;
}
/* 본인인증 옵션 */
.findidentity {
  border: 1px solid gray;
  border-radius: 2%;
}
.findidentitybutton {
  padding: 0 3%;
  border-style: none;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-top: 2%;
  margin-bottom: 2%;
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
  padding: 2%;
  border-radius: 3%;
  margin-bottom: 2%;
}
.identity-phon {
  border: 1px solid gray;
  border-radius: 5%;
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
  border-radius: 2%;
  margin-top: 2%;
}
.findemailbutton {
  padding: 0 3%;
  border-style: none;
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-top: 2%;
  margin-bottom: 2%;
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
  margin-top: -1%;
  box-shadow: none;
}
.emaillabel {
  margin-top: 3%;
  padding: 0 3%;
}
.email-control {
  margin-top: -1%;
  box-shadow: none;
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
</style>
