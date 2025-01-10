<script setup>
import { ref, reactive, defineProps, defineEmits } from 'vue';
import { useAuthStore } from '@/stores/auth';
const auth = useAuthStore();
const find = reactive({
  username: '',
  userId: '',
});

// find로 db상에 회원 정보(usernmame, userId)가 존재하는지 확인하는 로직작성.

// Props와 Emit 사용
const props = defineProps({
  ismodalvisit: Number,
});
const emit = defineEmits(['closemodal']);

// 모달 외부 클릭 시 닫기
const closeModalOutside = (event) => {
  if (event.target.classList.contains('modal')) {
    emit('closemodal');
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
  <div class="modal" v-if="ismodalvisit" @click="closeModalOutside">
    <div class="modal-title" @click.stop>
      <div class="mb-4" style="font-size: xx-large">
        <span style="padding: 0 3%"> 아이디 찾기 </span>
        <button
          class="id"
          type="button"
          style="border-style: none; background-color: white"
          v-show="ismodalvisit === 1"
        >
          비밀번호 찾기
        </button>
      </div>

      <!-- 본인인증 옵션 -->
      <div
        class="findidentity"
        style="
          border-style: solid;
          border-width: 1px;
          border-color: gray;
          border-radius: 2%;
        "
      >
        <button
          class="findidentitybutton mb-2 mt-2"
          type="button"
          style="
            padding: 0 3%;
            border-style: none;
            background-color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
          "
          @click="toggleidentity"
        >
          <span style="font-weight: bold"> 본인인증으로 찾기 </span>
          <span> {{ openidentity ? '∨' : '∧' }} </span>
        </button>
        <div v-if="openidentity" class="inidentity mb-2">
          <div
            style="
              border-style: solid;
              border-width: 1px;
              border-color: gray;
              border-radius: 5%;
              max-width: 28%;
              display: flex;
              justify-content: center;
              /* 수평 가운데 정렬 */
              text-align: center;
              /* 텍스트 가운데 정렬 */
              margin: 0 3%;
              /*좌우 이동*/
              flex-wrap: wrap;
            "
          >
            <img class="mt-2" src="@\assets\images\identity verification.png" />
            <p>휴대폰 본인인증</p>
          </div>
          <p class="mt-2" style="color: gray; margin-left: 2%">
            *본인명의 휴대폰이 아닌 경우, 인증이 불가합니다.
          </p>
        </div>
      </div>

      <!-- 이메일 옵션 -->
      <div
        class="findemail mt-2"
        style="
          border-style: solid;
          border-width: 1px;
          border-color: gray;
          border-radius: 2%;
        "
      >
        <button
          class="findemailbutton mt-2 mb-2"
          type="button"
          style="
            padding: 0 3%;
            border-style: none;
            background-color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
          "
          @click="toggleemail"
        >
          <span style="font-weight: bold"> 이메일로 찾기</span>
          <span> {{ openemail ? '∨' : '∧' }} </span>
        </button>
        <div v-if="openemail" class="inemail">
          <div class="namewindow mt-3" style="padding: 0 3%">이름</div>
          <div class="input-container mt-2" style="padding: 0 2%">
            <label class="nemelabel" for="name"></label>
            <input
              class="name-control"
              type="text"
              style="box-shadow: none"
              id="name"
              v-model="find.username"
              placeholder="이름을 입력하세요"
            />
          </div>
          <div class="emaillabel mt-3" style="padding: 0 3%">이메일</div>
          <div class="input-container mt-2 mb-3" style="padding: 0 2%">
            <label class="emaillabel" for="email"></label>
            <input
              class="email-control"
              type="text"
              style="box-shadow: none"
              id="email"
              v-model="find.userId"
              placeholder="이메일을 입력하세요"
            />
          </div>
          <button
            class="check mb-3"
            type="submit"
            style="
              padding: 0 5%;
              margin: 0 3%;
              width: 70%;
              border: 1px solid gray;
              background-color: gainsboro;
            "
          >
            확인
          </button>
        </div>
      </div>

      <button
        class="pw"
        type="button"
        style="border-style: none; background-color: white"
        v-show="ismodalvisit === 2"
      >
        아이디 찾기 비밀번호 찾기
      </button>
    </div>
  </div>
</template>

<style scoped>
.modal {
  position: fixed;
  z-index: 9999;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-title {
  background: white;
  padding: 3%;
  border-radius: 10px;
  width: 30%;
  height: 68%;
  margin: 10% auto;
}
</style>
