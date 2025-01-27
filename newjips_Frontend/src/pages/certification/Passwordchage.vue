<script setup>
import { ref, reactive } from 'vue';
import authApi from '@/api/authApi';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

const auth = useAuthStore();
const newPassword = ref('');
const confirmPassword = ref('');
const router = useRouter();

const member = reactive({
  userId: '',
  password: '',
});

const updatepassword = async () => {
  if (newPassword.value !== confirmPassword.value) {
    alert('비밀번호가 같지 않습니다.');
    return;
  }

  //비밀번호 변경 API 호출
  try {
    const formData = new FormData();
    formData.append('newPassword', newPassword.value);
    formData.append('userId', member.userId); // userId 추가
    console.log('Form Data:', formData);

    const response = await authApi.updatepassword(formData);
    console.log('비밀번호 변경 결과: ', response);

    alert('비밀번호가 성공적으로 변경되었습니다!');
    newPassword.value = '';
    router.push({ name: 'login' }); // 페이지 이동 설정
  } catch (error) {
    console.error('비밀번호 변경 오류: ', error);
    alert('비밀번호 변경에 실패했습니다. 다시 시도해 주세요.');
  }
};
</script>
<template>
  <body class="bg-light">
    <h1>비밀번호 변경</h1>
    <form @submit.prevent="updatepassword">
      <div>
        <label for="newPassword">새 비밀번호:</label>
        <input
          type="password"
          v-model="newPassword"
          id="newPassword"
          required
        />
      </div>
      <div>
        <label for="confirmPassword">비밀번호 확인:</label>
        <input
          type="password"
          v-model="confirmPassword"
          id="confirmPassword"
          required
        />
      </div>
      <button type="submit">변경하기</button>
    </form>
  </body>
</template>

<style scope></style>
