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
</script>

<template>
  <!-- 배경색 -->
  <body class="bg-light">
    <main class="page-wrapper d-flex flex-column" style="min-height: 100vh">
      <div
        class="container-fluid d-flex h-100 align-item-center justify-content-center py-4"
      >
        <!-- style="max-width: 100%; height: 100vh;" > 이러면 화면 길어짐;;;; -->
        <div class="card card-body" style="max-width: 70%; height: 600px">
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
                <router-link
                  to="/auth/login/certification?mode=id"
                  class="findid"
                  style="
                    color: gray;
                    border-style: none;
                    text-decoration: none;
                    background-color: white;
                  "
                >
                  아이디 찾기
                </router-link>
                <span style="color: gray">&vert;</span>
                <router-link
                  to="/auth/login/certification?mode=password"
                  class="findpw"
                  style="
                    color: gray;
                    border-style: none;
                    text-decoration: none;
                    background-color: white;
                  "
                >
                  비밀번호 찾기
                </router-link>
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

<style scoped></style>
