<script setup>
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';

const { t, locale } = useI18n();
const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

const member = reactive({
  userId: '',
  password: '',
});

const error = ref('');

const disableSubmit = computed(() => !(member.userId && member.password));

const login = async () => {
  console.log(member);
  try {
    await auth.login(member);

    // 일반
    router.push('/');
  } catch (e) {
    // 로그인 에러
    console.log('에러=======', e);
    if (e.response && e.response.status === 401) {
      //인증 실패
      error.value = '아이디 또는 비밀번호가 틀렸습니다.';
      alert(t('common.login1.notid'));
    } else {
      alert(t('common.login1.binid'));
      // error.value = e.response && e.response.data ? e.response.data : '로그인 중 에러 발생';
    }
  }
};

const passwordHidden = ref(true);
const togglePasswordVisibility = () => {
  passwordHidden.value = !passwordHidden.value;
};

const ismodalvisit = ref(0);
console.log('모달 : ', ismodalvisit.value);
const openmodal = (index) => {
  ismodalvisit.value = index;
  console.log('모달 열림', ismodalvisit.value);
};

const closemodal = () => {
  ismodalvisit.value = false;
  console.log('모달 닫힘');
};

const openidentity = ref(true);

const toggleidentity = () => {
  openidentity.value = !openidentity.value;
};
console.log('본인인증 창 열림 : ', openidentity.value);
</script>

<template>
  <!-- 배경색 -->
  <body class="bg-light">
    <main class="page-wrapper d-flex flex-column" style="min-height: 100vh">
      <div
        class="container-fluid d-flex h-100 align-item-center justify-content-center py-4"
      >
        <!-- style="max-width: 100%; height: 100vh;" > 이러면 화면 길어짐;;;; -->
        <div class="card card-body" style="max-width: 70%; max-height: 600px">
          <a
            class="position-absolute top-1 end-0 nav-link fs-sm py-1 px-2 me-3"
            href="#"
            onclick="window.history.go(-1); return false;"
          >
            <i class="fa-solid fa-arrow-left fs-base me-2"></i>Go back</a
          >
          <div class="row mx-0 align-item-center">
            <!-- 왼쪽화면 -->
            <div
              class="col-md-6 border-end-md p-sm-1"
              style="
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-top: 3%;
              "
            >
              <div>
                <h3 class="mt-2">{{ t('common.login1.hellow1') }}</h3>
                <h3>{{ t('common.login1.hellow2') }}</h3>
              </div>
              <img
                src="@/assets/images/Loginimg.png"
                style="height: 100%; max-height: 60%"
              />
            </div>

            <!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ오른쪽 화면ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
            <!-- 좌우패딩 위쪽패딩 아래쪽 패딩 text바 너비          toppadding -->
            <div class="col-md-6 mb-5 px-2 pt-2 pb-4 px-sm-2 pb-sm-5 pt-md-5">
              <form
                class="needs-validation mt-5"
                @submit.prevent="login"
                novalidate
              >
                <div class="mb-4">
                  <label class="form-label mb-2 mt-5" for="signin-email">{{
                    t('common.login1.id')
                  }}</label>
                  <input
                    class="form-control"
                    type="email"
                    id="signin-email"
                    v-model="member.userId"
                    :placeholder="t('common.login1.idinput')"
                    required
                  />
                </div>
                <div class="mb-4">
                  <div
                    class="d-flex align-items-center justify-content-between mb-2"
                  >
                    <label class="form-label mb-0" for="signin-password">{{
                      t('common.login1.password')
                    }}</label>
                  </div>
                  <div
                    class="change d-flex align-items-center"
                    style="position: relative"
                  >
                    <input
                      :type="passwordHidden ? 'password' : 'text'"
                      class="form-control"
                      type="password"
                      id="signin-password"
                      v-model="member.password"
                      :placeholder="t('common.login1.passwordinput')"
                      style="padding-right: 40px"
                      required
                    />
                    <!-- 비밀번호 암호화 해지 버튼 -->
                    <span
                      :class="
                        passwordHidden
                          ? 'fa-solid fa-eye-slash'
                          : 'fa-solid fa-eye'
                      "
                      @click="togglePasswordVisibility"
                      style="
                        position: absolute;
                        right: 10px;
                        top: 50%;
                        transform: translateY(-50%);
                        cursor: pointer;
                      "
                    ></span>
                  </div>
                </div>

                <!-- 아이디 | 비밀번호 찾기 -->
                <button
                  class="findid"
                  type="button"
                  @click="openmodal(1)"
                  :class="{ active: ismodalvisit === 1 }"
                  style="
                    color: gray;
                    border-style: none;
                    background-color: white;
                  "
                >
                  아이디 찾기
                </button>
                <span style="color: gray">&vert;</span>
                <button
                  class="findpw"
                  type="button"
                  @click="openmodal(2)"
                  :class="{ active: ismodalvisit === 2 }"
                  style="
                    color: gray;
                    border-style: none;
                    background-color: white;
                  "
                >
                  비밀번호 찾기
                </button>
                <div class="modal" v-if="ismodalvisit">
                  <div class="modal-title">
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
                    <!-- 본인인증 버튼 -->
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
                        <span style="font-weight: bold">
                          본인인증으로 찾기
                        </span>
                        <span> {{ openidentity ? '∨' : '∧' }} </span>
                      </button>
                      <div v-if="openidentity" class="toggle mb-2">
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
                          <img
                            class="mt-2"
                            src="@\assets\images\identity verification.png"
                          />
                          <p>휴대폰 본인인증</p>
                        </div>
                        <p class="mt-2" style="color: gray; margin-left: 2%">
                          *본인명의 휴대폰이 아닌 경우, 인증이 불가합니다.
                        </p>
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
                    <div class="closebutton mt-5">
                      <button
                        @click="closemodal"
                        style="border-style: none; background-color: white"
                      >
                        닫기
                      </button>
                    </div>
                  </div>
                </div>

                <!-- 로그인 버튼 -->
                <button
                  class="btn-orange btn-lg w-100 mt-4 mb-4"
                  type="submit"
                  :disabled="disableSubmit"
                >
                  {{ t('common.login1.login') }}
                </button>
              </form>
              <div class="mt-4 mt-sm-5">
                {{ t('common.login1.account') }}
                <router-link to="/auth/join" style="color: #ff8f17">{{
                  t('common.login1.join')
                }}</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </body>
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
  width: 28%;
  height: 50%;
  margin: 10% auto;
}
</style>
